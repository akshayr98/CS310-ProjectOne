<h1 id="collagetitle">Collage for topic $topicString</h1>
<button type="button" class="button" id="exportbutton" onclick="alert('Export Initiated')">Export Collage</button>
<div id="collagecontainer">
	<span id="collageinner">
		<img id="collage" alt="Insufficient number of images found" src=$imgURL>
	</span>
</div>
<div id="formcontainer">
	<form action="collageBuilder.java">
		<input type="text" id="searchterm" placeholder="Enter topic">
		<input type="button" id="searchbutton" class="button" value="Build Another Collage">
	</form>
</div>
<div class="previouscollagecontainer">
	$tableContents
</div>