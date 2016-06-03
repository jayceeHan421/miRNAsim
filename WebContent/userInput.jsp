<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>microRNA similarity</title>

<link href="css/userInput.css" rel="stylesheet" type="text/css" />
</head>
<center><h1> ${message}</h1></center>
<script>
function validateForm() {
    var x = document.forms["check"]["miRNA1"].value;
    var y = document.forms["check"]["Password"].value;
    if (y != z) {
        alert("Password must match password confirmation.");
        return false;
    }
}
</script>
<body>
<form id = "check" action= "UserInput" method = "post">
<div id="wrapper">

	<div id="header" class = "container">
		<div id = "logo">
		<div class = "image"></div><div class = "text"> Human microRNA Similarity Analysis</div>

		</div>
	<div class = "line" >
		<hr>
	</div>
	</div>
	<div id ="banner">
		<div class = "container">
			<div class="title">
				<h2> microRNA Similarity</h2>
				<span class = "byline"> microRNa Similarity Based on GO, pfam and pathway</span>
			</div>
		</div>
	</div>
	<div id = "extra" class = "container">
		<div id = "extra-block"> 
		<label>miRNA One:</label>		
		<div id = "searchbox1">	
		 <input id = "search" type = "text" placeholder = "input here" name = "miRNA1">
		</div>
		</div>
		<div id = "extra-block">
		<label> miRNA Two:</label>
	    <div id = "searchbox2">	
		<input id = "search" type = "text" placeholder = "input here" name = "miRNA2" >
		</div>
		</div>
		<div class = "title">
		<h2> Please Choose Your Database</h2>
		<div id = "three-column">
			<div class = "boxA">
			    <div><input type = "radio" name = radios value = "miRDB_microRNAgenes"></div>
				<div class = "box"><span class = "fa "><input type = "image" src="images/miRDB.png" ></span> 
				</div>
			</div>
			<div class = "boxB">
				<div><input type = "radio" name = radios value = "targetScan_microRNAgenes"></div>
				<div class = "box"><span class = "fa "><input type = "image" src="images/TargetScan.png"></span>
				</div>
			</div>
			<div class = "boxC">
				<div><input type = "radio" name = radios value = "miRTarBase_microRNAgenes"></div>
				<div class = "box"><span class = "fa "><input type = "image" src="images/miRTarBase.png"></span>
				</div>
			</div>
		</div>
		
		<center><input type = "submit" name = "getData" onclick = "this.form.action = 'UserInput'"></center>
		</div>
	</div>

</div>
<div id="copyright" class="container">
	<div>
		<p> Contact Us</p>
	</div>
	<p>&copy; SBBI Lab, UNL . | UNL, 122B/122C Avery Hall, 1144 T St, Lincoln, NE 68588-0115 | Phone: (402) 472-5023 | email us at bioinfo@cse.unl.edu.</p>
</div>
</form>
</body>
</html>