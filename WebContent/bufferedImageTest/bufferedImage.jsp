<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Image Test</title>
</head>
<body>

	<h1>Image Test</h1>
	
	<!-- click button to test AJAX call -->
	<button id="testButton">Click to Test!</button>
	
	<!-- container for images -->
	<div id="testDiv"></div>
	
	
<!-- using jQuery for AJAX call (doGet) -->
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	var testButton = document.getElementById("testButton");
	var div = document.getElementById("testDiv");
	
	// click button to trigger servlet call
	testButton.onclick = function() {
		
		// call myServlet doGet()
		$.get("${pageContext.request.contextPath}/ImageServlet", function(response) {
			
			// for every element in the string list
			$.each(response, function(index, s) {
				
				// create a new image element
				var imgTest = document.createElement("img");
				
				// response contains a Base64 string that will be converted to an image 
				// set image source to "data:image/png;base64, [byte array string]"
				// to display image
				imgTest.src = "data:image/png;base64, "+ s; 
				
				// append to div
				div.appendChild(imgTest);
				div.appendChild(document.createElement("br"));
				
				// So what's going on here is that we're passing the image as a base 64 string from
				// the back-end, which is then converted back to an image
			});
		});
	};
</script>
</body>
</html>
