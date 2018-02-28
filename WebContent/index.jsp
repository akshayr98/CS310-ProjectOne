<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>SCollage</title>
		<link rel="stylesheet" type="text/css" href="index.css">
	</head>
	<body>
		<!-- MAIN CONTENT DIV -->
		<div id="content">
			<!-- FORM CONTAINER -->
			<div id="formcontainer">
				<!-- SEARCH BOX INPUT FIELD -->
				<input type="text" id="searchtext" placeholder="Enter topic">
				<!-- BUILD COLLAGE SEARCH BUTTON -->
				<button id="searchbutton">Build Collage</button>
			</div>
		</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script>
		var searchButton = document.querySelector("#searchbutton"); // Grabs searchbutton html object
		var searchText = document.querySelector("#searchtext"); // Grabs searchtext text field object
		searchButton.setAttribute("disabled", "true"); // Disables the button until further action
		
		// Button to initiate server-side logic
		searchButton.onclick=function() {
			// Prepare variables to send data to servlet
			searchText = searchText.value; // The search topic input by user
			var browserWidth = $(window).width(); // Browser viewport width at time of click
			var browserHeight = $(window).height(); // Browser viewport height at time of click
			console.log(searchText);
			
			// AJAX call to Servlet
			$.ajax({
				// Type of request
				type:"GET",
				// Target for AJAX call
				url:"collageBuilderServlet",
				// Establish data to send to servlet
				data: {
					searchText: searchText,
					browserWidth: browserWidth,
					browserHeight: browserHeight
				},
				// Completes when response received
				success: function(response) {
					console.log('success');
					// Redirect to collage.jsp
					window.location="collage.jsp";
				}
			})
		}
		
		// Function to check if search text box is empty on keyup
		searchText.onkeyup=function() {
			// If not empty
			if (searchText.value.length > 0) {
				// Removes the disabled attribute
				searchButton.removeAttribute("disabled");
			}
			else {
				// Disables the build button
				searchButton.setAttribute("disabled", "true");
			}
		}
		
		// Function to enable functionality to press enter key to search
		document.addEventListener("keyup", function(event) {
			if (event.keyCode == 13) {
				searchButton.click();
			}
		})
	</script>
	</body>
</html>




