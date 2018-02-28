<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>SCollage</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script>
			$(document).ready(function() 
			{
				console.log("DEBUG: doc ready");
				$('#searchbutton').click(function(event)
				{
					event.preventDefault(); // prevent submit
					console.log("DEBUG: submitted");
					var searchText = document.getElementById("searchtext").value; // get value from form
					var browserWidth = $(window).width(); // browser viewport width
					var browserHeight = $(window).height(); // browser viewport height
					console.log(searchtext);
					$.ajax(
					{
						type: "GET",
						url: "collageBuilderServlet",
						data: // pass data
						{
							searchText: searchText,
							browserWidth: browserWidth,
							browserHeight: browserHeight
							
						}, // data end
						success: function(response)
						{
							console.log("success");
							setTimeout(function(){
								window.location.replace("collagepages/" + response);
							}, 1000);
						} // end success
						
					}); //end ajax
					console.log("DEBUG: ajax ended");
					return false;
				}); // end button click 
				
		 		var searchbutton = document.querySelector("#searchbutton");
				var formAction = document.querySelector("#searchform");
				searchbutton.setAttribute("disabled", "true");
				var searchtext = document.querySelector("#searchtext");
				searchtext.onkeyup=function() {
					if (searchtext.value.length > 0) {
						searchbutton.removeAttribute("disabled");
					}
					else {
						searchbutton.setAttribute("disabled", "true");
					}
				}
			})// end doc ready
		</script>
		<link rel="stylesheet" type="text/css" href="index.css">
	</head>
	<body>
		<div id="content">
			<form id="searchform">
				<input type="text" id="searchtext" placeholder="Enter topic">
				<span>
					<button type="button" id="searchbutton" disabled="disabled">Build Collage</button>
				</span>
			</form>
		</div>
	</body>
</html>




