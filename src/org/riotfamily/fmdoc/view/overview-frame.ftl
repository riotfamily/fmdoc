<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="stylesheet.css" />
</head>
<body id="overview-frame">
	<h4>All Templates</h4>
	<ul>
		<#list templates as template>
			<li><a href="${template.href}" target="doc">${template.namespace}</a></li>
		</#list>
	</ul>
</body>
</html>