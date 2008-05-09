<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="stylesheet.css" />
</head>
<body id="overview-summary">
	<div class="nav">
		<span>Overview</span> <a href="index-all.html">Index</a>
	</div>
	<table class="summary">
		<thead>
			<tr>
				<th colspan="2">Template Summary</th>
			</tr>
		</thead>
		<tbody>
			<#list templates as template>
				<tr>
					<td><a href="${template.href}">${template.namespace}</a></td>
					<td>${(template.comment.description)!}</td>
				</tr>
			</#list>
		</tbody>
	</table>
</body>
</html>