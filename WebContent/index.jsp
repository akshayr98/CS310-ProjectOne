<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>SCollage</title>
		<link rel="stylesheet" type="text/css" href="index.css">
	</head>
	<body>
		<!-- Main content div -->
		<div id="content">
			<div id="formcontainer">
				<input type="text" id="searchtext" placeholder="Enter topic">
				<button id="searchbutton">Build Collage</button>
			</div>
		</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script>
		var searchButton = document.querySelector("#searchbutton");
		var searchText = document.querySelector("#searchtext"); // get value from form
		searchButton.setAttribute("disabled", "true");
		
		searchButton.onclick=function() {
			searchText = searchText.value;
			var browserWidth = $(window).width(); // browser viewport width
			var browserHeight = $(window).height(); // browser viewport height
			console.log(searchText);
			$.ajax({
				type:"GET",
				url:"collageBuilderServlet",
				data:
				{
					searchText: searchText,
					browserWidth: browserWidth,
					browserHeight: browserHeight
				},
				success: function(response)
				{
					console.log('success');
					window.location="collage.jsp";
				}
			})
		}
		
		searchText.onkeyup=function() {
			if (searchText.value.length > 0) {
				searchButton.removeAttribute("disabled");
			}
			else {
				searchButton.setAttribute("disabled", "true");
			}
		}
		
		document.addEventListener("keyup", function(event) {
			if (event.keyCode == 13) {
				searchButton.click();
			}
		})
		
	</script>
	</body>
</html>




