<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="classes.*"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="java.io.*"%>
<%@ page import="java.io.IOException"%>
<%@ page import="javax.imageio.ImageIO"%>
<%@ page import="java.util.Base64"%>

<!DOCTYPE html>
<html>
<head>
	<title>Collage Builder</title>

	<style>
		#collage {
			width: 100%
			float: left;
			vertical-align: middle;
			text-align: center;
			margin: 0;
		}
		#main {
			min-width: 800px;
			min-height: 600px;
			width: 40vw;
			height: 50vh;
			margin: 0;
		}
		#prev {
			height: 120px;
			padding-top: 20px;
			text-align: center;
			overflow: auto;
			white-space: nowrap;		
		}
		#title {
			text-align: center;
		}
		#inputDiv {
			text-align: center;
			padding-top: 10px;
		}
		#prev .imgContainer {
			height: 100px;
			width: 100px;
			display: inline;
		}
		
		#prev .imgContainer {
			padding-right: 10px;
			padding-left: 10px;
		}
		#prev .imgContainer img {
			height: 98px;
		}
		#button {
			background-color: #2d2d2d;
			color: white;
			border: none;
			height: 25px;
		}
		#button:hover:enabled {
			background-color: #dbdbdb;
			colo: #2d2d2d;
		}
		img {
			text-align: center;
		}
		
		#error {
			min-width: 800px;
			min-height: 600px;
			width: 40vw;
			height: 50vh;
			border: 1px solid red;
			margin: 0;
		}
	</style>
</head>
<body>
<%

CollageManager collageManager = (CollageManager) request.getSession(false).getAttribute("collageManager");

Vector<BufferedImage> collages = collageManager.getCollages();
Vector<String> collageTitle = collageManager.getCollageTitles();
ByteArrayOutputStream byteArrayOS;
byte[] imageBytes;
String base64String;
%>

	<!-- TITLE -->
	<div id="title"><h1>Collage for Topic <%=collageTitle.get(collageTitle.size()-1)%></h1></div>
	
	<!-- EXPORT BUTTON -->
	<div id="exportDiv"><button id="export">Export as PNG</button></div>
	
	<!-- MAIN COLLAGE -->
	<%
	byteArrayOS = new ByteArrayOutputStream();
	ImageIO.write(collages.get(collages.size()-1), "png", byteArrayOS);
	imageBytes = byteArrayOS.toByteArray();
	imageBytes = Base64.getEncoder().encode(imageBytes);
	base64String = new String(imageBytes, "UTF-8");
	%>
	<div id="collage"><img id="main" src="data:image/png;base64,<%=base64String%>" alt="<%=collageTitle.get(collageTitle.size()-1)%>"></div>
	
	<!-- TEXT BOX AND BUTTON -->
	<div id="inputDiv">
		<input id="textInput" type="text" placeholder="Enter Topic">
		<button id="button">Build Another Collage</button>
	</div>
	
	<!-- PREVIOUS COLLAGES -->
	<div id="prev">
		<%
		for (int i = 0; i < collages.size()-1; i++) {
		%>
			<div class="imgContainer">
				<%
				byteArrayOS = new ByteArrayOutputStream();
				ImageIO.write(collages.get(i), "png", byteArrayOS);
				imageBytes = byteArrayOS.toByteArray();
				imageBytes = Base64.getEncoder().encode(imageBytes);
				base64String = new String(imageBytes, "UTF-8");
				%>
				<img src="data:image/png;base64,<%=base64String%>" alt="<%=collageTitle.get(i)%>">
			</div>
		<%
		}
		%>
	</div>
	<div id="container"></div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
	
	var title=document.querySelector("h1");
	var textInput=document.querySelector("#textInput");
	var prev=document.querySelector("#prev");
	var buildCollage=document.querySelector("#button");
	var children=prev.getElementsByTagName("img");
	var main=document.querySelector("#main");
	var exportButton=document.querySelector("#export");
	var collage=document.querySelector("#collage");
	
	exportButton.onclick=function() {
		var a = $("<a>").attr("href",main.src).attr("download", "imagename.png").appendTo("body");
		a[0].click();
		a.remove();
	}
	
	// swapping with previous collages
	prev.onclick=function() {
		for (i=0; i<children.length; i++) {
			(function(i) {
				children[i].onclick=function() {
					var childrenTemp = children[i].src;
					var childrenAlt = children[i].alt;
					alert(childrenAlt);
					for (j=i; j<children.length; j++) {
						(function(i) {
							if (j == children.length-1) {
								if (collage.innerHTML=="Insufficient Number of Images Found") {									
									// move clicked image to main
									alert("Inside if: "+childrenAlt);
									title.innerHTML = "Collage for topic "+children[i].alt;
									
									// remove last img element if insufficient images found
								} else {
									children[j].src=main.src;
									children[j].alt=main.alt;
									main.src=childrenTemp;
									main.alt=childrenAlt;
									title.innerHTML = "Collage for topic "+childrenAlt;
								}
							} else {
								children[j].src=children[j+1].src;
								children[j].alt=children[j+1].alt;
							}
						})(j);
					}
				}
			})(i);
		}
	}
	// disable/enable button depending on text input
	buildCollage.disabled=true;
	textInput.onkeyup=function() {
		if (textInput.value.length <= 0) {
			buildCollage.disabled=true;
		}
		else if (textInput.value.length > 0) {
			buildCollage.disabled=false;
		}
	}
	
	/*
	buildCollage.onclick=function(event) {
		var searchText = textInput.value;
		console.log(searchText);
		$.ajax({
			type:"GET",
			url:"collageBuilderTestServlet",
			data:
			{
				searchText: searchText
			},
			success: function(response)
			{
				location.reload();
			}
		}); // end ajax
		console.log("DEBUG: ajax ended");
		return false;
	}
	*/
	
	buildCollage.onclick=function(event) {
		var searchText = textInput.value;
		console.log(searchText);
		$.ajax({
			type:"GET",
			url:"collageBuilderTestServlet",
			data:
			{
				searchText: searchText
			},
			success: function(response)
			{
				if (response == "success") {
					location.reload();
				}
				else {
					alert('fail');
					var newDiv=document.createElement("div");
					newDiv.classList.add("imgContainer");
					var newImage=document.createElement("img");
					newImage.src=main.src;
					newDiv.appendChild(newImage);
					prev.appendChild(newDiv);
					children=prev.getElementsByTagName("img");
					collage.innerHTML="Insufficient Number of Images Found";
				}
			}
		}); // end ajax
		console.log("DEBUG: ajax ended");
		return false;
	}
</script>
</body>
</html>