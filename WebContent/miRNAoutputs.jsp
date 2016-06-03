<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/outputs.css" rel="stylesheet" type="text/css" />
<title>outputs</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script language="JavaScript">
function toggle1(source) {
  checkboxes = document.getElementsByName('choices1');
  for(var i=0, n=checkboxes.length;i<n;i++)
	  checkboxes[i].checked = source.checked;
}
</script>


<script language="JavaScript">
function toggle2(source) {
  checkboxes = document.getElementsByName('choices2');
  for(var i=0, n=checkboxes.length;i<n;i++)
	  checkboxes[i].checked = source.checked;
}
</script>


<script src="https://d3js.org/d3.v3.min.js" charset="utf-8"></script>
<script>
var theData = '${jsonString}';
</script>
<style>

.node {
  stroke: #fff;
  stroke-width: 1.5px;
}

.link {
  stroke: #999;
  stroke-opacity: .6;
}
</style>

</head>
<body>

<form action = "Outputs" method = "post">
<div id = "wrapper">
<div id = "geneLists">
	<div class = "geneTable1">
		<table id = "geneTable">
		<thead>
			<tr>
			<th> miRNA One Target Genes</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			<td><input type = "checkbox" onClick = "toggle1(this)"/> Select All</td>
			</tr>
			<c:forEach items = "${themeList1}" var = "theme" varStatus = "row">
			<tr>
				<td><input type="checkbox" name="choices1" value = "${theme}"> ${theme} </td>
			</tr>
			</c:forEach>

		</tbody>
		</table>
	</div>
	<div class = "geneTable2">
		<table id = "geneTable">
		<thead>
			<tr>
			<th> miRNA Two Target Genes</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			<td><input type = "checkbox" onClick = "toggle2 (this)"/> Select All</td>
			</tr>
			<c:forEach items = "${themeList2}" var = "theme" varStatus = "row">
			<tr>
				<td><input type="checkbox" name="choices2" value = "${theme}"> ${theme}</td>
			</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>
	<input type = "submit" name = "submit"> 
</div>
<div id = "main">
<div id = "PPI" >
<script>

var width = 960,
    height = 500;

var color = d3.scale.category20();

var force = d3.layout.force()
    .charge(-120)
    .linkDistance(30)
    .size([width, height]);

var svg = d3.select("body").append("svg")
    .attr("width", width)
    .attr("height", height);

//d3.json(theData, function(error, graph) {
graph = JSON.parse(theData);
  //if (error) throw error;

  force
      .nodes(graph.nodes)
      .links(graph.links)
      .start();

  var link = svg.selectAll(".link")
      .data(graph.links)
    .enter().append("line")
      .attr("class", "link")
      .style("stroke-width", function(d) { return Math.sqrt(d.value); });

  var node = svg.selectAll(".node")
      .data(graph.nodes)
    .enter().append("circle")
      .attr("class", "node")
      .attr("r", 5)
      .style("fill", function(d) { return color(d.group); })
      .call(force.drag);

  node.append("title")
      .text(function(d) { return d.name; });

  force.on("tick", function() {
    link.attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });

    node.attr("cx", function(d) { return d.x; })
        .attr("cy", function(d) { return d.y; });
  });
//});
</script>
</div>


<div id = pathwayEnrichment>
	<div id = pathway1>
	<table style = "float:left">
		<thead>
			
		</thead>
		<tbody>
			<c:forEach var = "pathway1" items = "${pathwayLists1}">
			<tr>
			<td> ${pathway1.pathwayName} </td>
			<td> ${pathway1.pvalue }</td>
			<td> ${pathway1.occurrence }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div id = pathway2>
		<table style = "float:left">
		<thead>
			
			</thead>
			<tbody>
				<c:forEach items = "${pathwayLists2}" var = "pathway2" varStatus = "row">
				<tr>
					<td> ${pathway2.pathwayName} </td>
					<td> ${pathway2.pvalue} </td>
					<td> ${pathway2.occurrence} </td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</div>
</div>

	<div id = miRNAsim >
	<textarea name = "GOvalue">
	${miRNAsim}
	</textarea>
	</div>
</div>
</form>

</body>
</html>