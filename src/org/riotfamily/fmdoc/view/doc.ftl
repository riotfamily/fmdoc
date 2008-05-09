<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${template.namespace}</title>
	<link rel="stylesheet" type="text/css" href="stylesheet.css" />
</head>
<body id="template">
<div class="nav">
	<a href="overview-summary.html">Overview</a> <a href="index-all.html">Index</a>
</div>
<h1>${template.namespace}</h1>
<#if template.comment??>
	<@comment template.comment />
</#if>

<table class="summary">
	<thead>
		<tr>
			<th colspan="2">Summary</th>
		</tr>
	</thead>
	<tbody>
		<#if template.variables??>
			<#list template.variables as var>
				<tr>
					<td class="type">Variable</td>
					<td><a href="#${var.name}">${var.name}</a></td>
				</tr>
			</#list>
		</#if>
		<#if template.macros??>
			<#list template.macros as macro>
				<tr>
					<td class="type">${macro.function?string('Function', 'Macro')}</td>
					<td><a href="#${macro.name}">${macro.name}</a></td>
				</tr>
			</#list>
		</#if>
	</tbody>
</table>


<#if template.variables??>
	<h2>Global Variables</h2>
	<#list template.variables as var>
		<@comment var />
		<#if var_has_next>
			<hr />
		</#if>	
	</#list>
</#if>
<#if template.macros??>
	<h2>Macros &amp; Functions</h2>
	<#list template.macros as macro>
		<@comment macro />	
		<#if macro_has_next>
			<hr />
		</#if>
	</#list>
</#if>
</body>
</html>

<#macro comment comment>
	<#if comment.name??>
		<a name="${comment.name}"></a>
		<h3>${comment.name}</h3>
	</#if>
	<#if comment.signature??>
		<div class="signature">
			${comment.signature}
		</div>
	</#if>
	<#if comment.description??>
		<div class="description">
			${comment.description}
		</div>
	</#if>
	<#if comment.parameters??>
		<dl class="parameters">
			<dt>Parameters:</dt>
			<dd>
				<table class="parameters">
					<#list comment.parameters as param>
						<tr>
							<td class="name ${param.required?string('required', 'optional')}">${param.name}</td>
							<td class="description">
								${param.description!}
								<#if param.defaultValue??>
									Default is <code>${param.defaultValue}</code>.
								</#if>	
							</td>
						</tr>
					</#list>
				</table>
			</dd>
		</dl>
	</#if>
	<#if comment.returns??>
		<dl class="returns">
			<dt>Returns:</dt>
			<dd>${comment.returns}</dd>
		</dl>
	</#if>
	<#if comment.since??>
		<dl class="since">
			<dt>Since:</dt>
			<dd>${comment.since}</dd>
		</dl>
	</#if>
</#macro>