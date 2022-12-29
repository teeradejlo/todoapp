<template>
	<div id="app">
		<nav class="navbar-positioning navbar navbar-expand navbar-dark bg-dark">
			<router-link to="/" class="navbar-brand">teeradejlo</router-link>
			<div class="navbar-nav mr-auto">
				<li class="nav-item">
					<router-link to="/todos" class="nav-link">Todos</router-link>
				</li>
				<li class="nav-item">
					<router-link to="/add" class="nav-link">Add</router-link>
				</li>
			</div>
		</nav>

		<div class="view-container">
			<router-view />
		</div>
	</div>
</template>

<style>
.navbar-positioning {
	position: absolute !important;
	top: 0;
	left: 0;
	width: 100%;
}

.view-container {
	width: 100%;
	height: 100vh
}

/* width */
::-webkit-scrollbar {
	width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
	background: #f1f1f1;
	border-radius: 50px;
}

/* Handle */
::-webkit-scrollbar-thumb {
	background: rgb(216, 216, 216);
	border-radius: 50px;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
	background: rgb(176, 176, 176);
}
</style>
  
<script>
export default {
	name: "app"
};
</script>