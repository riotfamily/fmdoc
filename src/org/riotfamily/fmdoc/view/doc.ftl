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
	<@details template.comment />
</#if>
<dl class="path">
	<dt>Path:</dt>
	<dd>${template.path}</dd>
</dl>

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
					<td class="type">${var.type}</td>
					<td>
						<div class="signature"><a href="#${var.name}">${var.name}</a></div>
						<div class="description">${var.shortDescription!}</div>
					</td>
				</tr>
			</#list>
		</#if>
		<#if template.macros??>
			<#list template.macros as macro>
				<tr>
					<td class="type">${macro.type}</td>
					<td>
						<div class="signature"><a href="#${macro.name}">${macro.name}</a>${macro.shortArgs}</div>
						<div class="description">${macro.shortDescription!}</div>
					</td>
				</tr>
			</#list>
		</#if>
	</tbody>
</table>


<#if template.variables??>
	<h2>Global Variables</h2>
	<#list template.variables as var>
		<div class="variable">
			<@doc var />
		</div>
		<#if var_has_next>
			<hr />
		</#if>	
	</#list>
</#if>
<#if template.macros??>
	<h2>Macros &amp; Functions</h2>
	<#list template.macros as macro>
		<div class="macro">
			<@doc macro />
		</div>	
		<#if macro_has_next>
			<hr />
		</#if>
	</#list>
</#if>
</body>
</html>

<#macro doc doc>
	<#if doc.name??>
		<a name="${doc.name}"></a>
		<h3>${doc.name}</h3>
	</#if>
	<#if doc.signature??>
		<div class="signature">
			${doc.signature}
		</div>
	</#if>
	<@details doc />
</#macro>

<#macro details doc>
	<div class="details">
		<#if doc.description??>
			<div class="description">
				${doc.description}
			</div>
		</#if>
		<#if doc.parameters??>
			<dl class="parameters">
				<dt>Parameters:</dt>
				<dd>
					<#list doc.parameters as param>
							<span class="name ${param.required?string('required', 'optional')}">${param.name}</span>
							<span class="default">
								<#if param.required>
									(required)
								<#else>
									(optional, default is <code>${param.defaultValue}</code>)
								</#if>
							</span>
							<span class="description">
								${param.description!}
							</span>
					</#list>
				</dd>
			</dl>
		</#if>
		<#if doc.returns??>
			<dl class="returns">
				<dt>Returns:</dt>
				<dd>${doc.returns}</dd>
			</dl>
		</#if>
		<#if doc.since??>
			<dl class="since">
				<dt>Since:</dt>
				<dd>${doc.since}</dd>
			</dl>
		</#if>
		<#if doc.seeAlso??>
			<dl class="see">
				<dt>See Also:</dt>
				<dd>
					<#list doc.seeAlso as see>
						${see}<br />
					</#list>
				</dd>
			</dl>
		</#if>
	</div>
</#macro>