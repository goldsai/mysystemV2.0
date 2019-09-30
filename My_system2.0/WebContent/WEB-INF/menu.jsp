<%@page import="java.util.Calendar"%>

<header class="mdl-layout_header">
	<div class="mdl-layuot_heaader-row">
		<!-- Title -->
		<span class="mdl-layout-title">Personal Stuff Management System</span>
		<!-- Add spacer, to align navigation to the right -->
		<div class="mdl-layout-spacer"></div>
		<!-- Navigation. We hide it in small screens. -->
		
		<nav class="mdl-navigation mld-layout--large-screen-only">
			<a class="mdl-navigation__link" href="/PSMS/new">Add New Stuff</a>
			<a class="mdl-navigation__link" href="/PSMS/list">List All Stuff</a>
		</nav>
	</div>
</header>
<div class="mdl=layout__drawer">
	<span class="mdl-layout-title">PSMS</span>
	<nav class="mdl-navigation">
		<a class="mdl-navigation__link" href="/PSMS/new">Add New Stuff</a>
		<a class="mdl-navigation__link" href="/PSMS/list">List All Stuff</a>
	</nav>
</div>