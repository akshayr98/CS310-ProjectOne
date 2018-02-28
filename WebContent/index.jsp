<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>SCollage</title>
		<link rel="stylesheet" type="text/css" href="index.css">
	</head>
	<body>
		<div id="content">
			<input type="text" id="textInput" placeholder="Enter topic">
			<button id="searchbutton">Build Collage</button>
		</div>
		
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script>
		var searchbutton=document.querySelector("#searchbutton");
		var textInput=document.querySelector("#textInput");
		
		searchbutton.onclick=function() {
			var searchText=textInput.value;
			console.log(searchText);
			$.ajax({
				type:"GET",
				url:"collageBuilderServlet",
				data:
				{
					searchText: searchText,
					page: "index"
				},
				success: function(response)
				{
					console.log('success');
					window.location="collagemakertest.jsp";
				}
			})
		}
	</script>
	</body>
</html>




