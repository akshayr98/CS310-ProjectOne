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
		<title>SCollage</title>
		<link rel="stylesheet" type="text/css" href="collage.css">
	</head>
	<body>
		<%
		// Grabs CollageManager object from session
		CollageManager collageManager = (CollageManager) request.getSession(false).getAttribute("collageManager");
		// Grabs the search string from session
		String searchText = (String) request.getSession(false).getAttribute("currentSearchText");
		// Vector of collages from CollageManager
		Vector<BufferedImage> collages = collageManager.getCollages();
		// Vector of collage titles from CollageManager
		Vector<String> collageTitle = collageManager.getCollageTitles();
		// ByteArrayOutputStream to convert the buffered images to base 64 string
		ByteArrayOutputStream byteArrayOS;
		// ByteArray to hold image bytes
		byte[] imageBytes;
		// Resultant base64 String for images
		String base64String;
		
		%>
		<!-- TITLE -->
		<div id="title"><h1>Collage for topic <%=searchText%></h1></div>
		
		<!-- EXPORT BUTTON -->
		<div id="exportdiv"><button id="export">Export Collage</button></div>
		
		<!-- MAIN COLLAGE -->
		<%
		// If collages is an empty vector, creates tag for error message
		if (collages.size() == 0) {
		%>
			<!-- Error Message Tag -->
			<div id="collage"><div id="error">Insufficient number of images found</div></div>
		<%
		}
		// If collages is not empty, converts the last collage to base 64 string for display.
		else {
			byteArrayOS = new ByteArrayOutputStream();
			ImageIO.write(collages.get(collages.size()-1), "png", byteArrayOS);
			imageBytes = byteArrayOS.toByteArray();
			imageBytes = Base64.getEncoder().encode(imageBytes);
			base64String = new String(imageBytes, "UTF-8");
		%>
			<!-- Main Collage Displaying Tag -->
			<div id="collage"><img id="main" src="data:image/png;base64,<%=base64String%>" alt="<%=collageTitle.get(collageTitle.size()-1)%>"></div>
		<%} %>
		
		<!-- TEXT BOX AND BUTTON -->
		<div id="inputdiv">
			<input id="searchtext" type="text" placeholder="Enter topic">
			<button id="searchbutton">Build Another Collage</button>
		</div>
		
		<!-- PREVIOUS COLLAGES -->
		<div id="prev">
			<%
			// Iterates through all collages in session
			for (int i = 0; i < collages.size()-1; i++) {
			%>
				<!-- CONTAINER FOR PREVIOUS COLLAGES -->
				<div class="previousimgcontainer">
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

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script>
			
			var title = document.querySelector("h1"); // Grabs title html object
			var searchText = document.querySelector("#searchtext"); // Grabs title html object
			var prev = document.querySelector("#prev"); // Grabs title html object
			var searchButton = document.querySelector("#searchbutton"); // Grabs title html object
			var previousCollages = prev.getElementsByTagName("img"); // Grabs title html object
			var main = document.querySelector("#main"); // Grabs main content space html object
			var exportButton = document.querySelector("#export"); // Grabs exportbutton html object
			var collage = document.querySelector("#collage"); // Grabs collage container html object
			var fail = false; // Flag for insufficient images found
			
			console.log(main.alt);
			
			// If no collage is currently displayed, disable export button
			if (document.querySelector("#error") != null) {
				exportButton.disabled = true;
			}
			else {
				exportButton.disabled = false;
			}
						
			// Export collage on button click
			exportButton.onclick=function() {
				var a = $("<a>").attr("href",main.src).attr("download", main.alt+" collage.png").appendTo("body");
				a[0].click();
				a.remove();
			}
			
			// Swapping with previous collages
			prev.onclick = swapCollages();
			function swapCollages() {
				// For every previous collage element
				for (i = 0; i < previousCollages.length; i++) {
					(function(i) {
						previousCollages[i].onclick=function() {
							// Grab src and alt
							var previousTemp = previousCollages[i].src;
							var previousAlt = previousCollages[i].alt;
							for (j = i; j < previousCollages.length; j++) {
								(function(j) {
									// If last element in previous collages is clicked
									if (j == previousCollages.length-1) {
										// If no collage is currently displayed
										if (document.querySelector("#error")!=null) {
											// Move clicked image to main collage space										
											var newImage = document.createElement("img");
											newImage.id = "main";
											newImage.src = previousTemp;
											newImage.alt = previousAlt;
											
											// Replace collage main space inner HTML with newImage
											collage.innerHTML = "";
											collage.appendChild(newImage);
											
											// Change title
											title.innerHTML = "Collage for topic " + previousAlt;
											
											// Remove last img element if insufficient images found	
											previousCollages[j].parentNode.removeChild(previousCollages[j]);
											previousCollages = prev.getElementsByTagName("img");
											main = document.querySelector("#main");
											
											// Disable export button if current collage is empty
											exportButton.disabled = false;
											fail = false;
										}
										// If there is a collage displayed
										else {
											// Swap source and alt
											previousCollages[j].src = main.src;
											previousCollages[j].alt = main.alt;
											main.src = previousTemp;
											main.alt = previousAlt;
											
											// Change title
											title.innerHTML = "Collage for topic " + previousCollagesAlt;
										}
									}
									// If not last element
									else {
										// Swap source and alt
										previousCollages[j].src = previousCollages[j+1].src;
										previousCollages[j].alt = previousCollages[j+1].alt;
									}
								})(j);
							}
						}
					})(i);
				}
			}
			
			// Disable/enable Build Another Collage button depending on text input
			searchButton.disabled = true;
			searchText.onkeyup = function() {
				
				// Disable if empty
				if (searchText.value.length <= 0) {
					searchButton.disabled = true;
				}
				
				// Enable if not empty
				else if (searchText.value.length > 0) {
					searchButton.disabled = false;
				}
			}
			
			// Enter key triggers Build Another Collage button
			document.addEventListener('keydown', function(event) {
				if (event.keyCode == 13) {
					searchButton.click();
				}
			})
			
			// If Build Another Collage button is clicked
			searchButton.onclick = function(event) {
				
				var searchTextToSend = searchText.value; // Grab user input value
				searchText.value = ""; // Reset search box
				var browserWidth = $(window).width(); // Browser viewport width
				var browserHeight = $(window).height(); // Browser viewport height
				
				// Initiate AJAX call
				$.ajax({
					type:"GET",
					url:"collageBuilderServlet",
					data:
					{
						// Attributes
						searchText: searchTextToSend,
						browserWidth: browserWidth,
						browserHeight: browserHeight
					},
					success: function(response) // Response contains status and title (if fail)
					{
						
						var res = response.split(" "); // Split response
						if (res[0] == "success") { // Collage successfully built
							location.reload(); // Refresh browser
							fail = false; // Set flag
						}
						else { // Collage failed to build
							if (fail == false) { // If a collage is currently displayed
								
								// Create a new div and img element
								var newDiv = document.createElement("div");
								newDiv.classList.add("previousimgcontainer");
								var newImage = document.createElement("img");
								// Set source and alt of new image
								newImage.src = main.src;
								newImage.alt = main.alt;
								// Append to previous collage picker div
								newDiv.appendChild(newImage);
								prev.appendChild(newDiv);
								// Reset previous collage object
								previousCollages = prev.getElementsByTagName("img");
								
								// Create "Insufficient number of images found" text
								newDiv = document.createElement("div");
								newDiv.id = 'error';
								newDiv.innerHTML = "Insufficient number of images found";
								
								// Set "Insufficient number of images found" text to
								// main collage area
								collage.innerHTML = "";
								collage.appendChild(newDiv);
								exportButton.disabled = true;
								
								title.innerHTML = "Collage for topic "+ res[1]; // Change title
								fail = true; // Set flag
								swapCollages(); // Reset onclick for previous collages
							}
							else { // If no collage is currently displayed
								title.innerHTML = "Collage for topic "+ res[1];
							}
						}
					}
				}); // end ajax
				return false;
			}
		</script>
	</body>
</html>