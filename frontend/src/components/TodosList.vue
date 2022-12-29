<template>
	<div class="page-body" style="background-color: rgb(188, 255, 208);">
		<div class="rounded-box fade-in">
			<div>
				<form @submit.prevent="searchTodo">
					<div class="d-flex">
						<input class="form-control" id="search-bar" v-model="searchTitle" name="search-bar"
							placeholder="Search by title..." style="max-width: 350px;" />
						<button class="btn btn-outline-secondary" type="submit">Search</button>
					</div>
				</form>
			</div>

			<div class="d-flex" style="min-width: 50vw; margin-top: 30px;">
				<div class="left-panel-tutsList">
					<h4>Todos List</h4>
					<div class="list-container-tutsList">
						<template v-if="todos.length != 0">
							<div class="list-item-tutsList"
								:class="[todo.id == selectedID ? 'selected-todo' : 'unselected-todo']"
								v-for="todo in this.todos" :key="todo.id" @click="selectTodo(todo, todo.id)">
								<span>{{ todo.title }}</span>
							</div>
						</template>
						<div v-else class="d-flex" style="height: 100%;">
							<div class="d-flex justify-content-center align-items-center flex-grow-1"
								style="height: 100%;">
								<p style="color: lightgrey;">No todo found...</p>
							</div>
						</div>
					</div>
					<button class="btn btn-danger" style="width: 100%; margin-top: 10px;" @click="removeAllTodo">
						Delete All
					</button>
				</div>
				<div v-if="selected" class="right-panel-tutsList">
					<h4>Todo</h4>
					<div>
						<label><strong>Title:</strong></label> {{ selectedTodo.title }}
					</div>
					<div>
						<label><strong>Description:</strong></label> {{ selectedTodo.description }}
					</div>
					<div>
						<label><strong>Status:</strong></label> {{ selectedTodo.published ? "Published" : "Pending" }}
					</div>
					<button class="btn btn-primary mt-2" style="padding-left: 20px; padding-right: 20px;"
						@click="$router.push('/todos/' + this.selectedID)">
						Edit
					</button>
				</div>
				<div v-else class="d-flex justify-content-center align-items-center flex-grow-1">
					<p style="color: lightgrey;">Select a Todo</p>
				</div>
			</div>
		</div>
	</div>
</template>

<style>
.left-panel-tutsList {
	width: 28%;
	margin-right: 5%;
}

.right-panel-tutsList h4 {
	margin-bottom: 15px;
}

.list-container-tutsList {
	width: 100%;
	height: 40vh;
	border: 1px solid #ccc;
	border-radius: 10px;
	overflow-y: auto;
}

.list-item-tutsList {
	padding: 10px 20px 10px 20px;
	border-bottom: 1px solid #ccc;
	background-color: white;
	overflow-x: auto;
}

.list-item-tutsList span {
	display: block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.selected-todo {
	font-weight: bold;
}

.unselected-todo:hover {
	cursor: pointer;
	background-color: rgb(232, 232, 232);
}
</style>

<script>
import "./common-styles.css";
import TodoDataService from "../services/TodoDataService";

export default {
	name: "todos-list",
	data() {
		return {
			searchTitle: "",
			selected: false,
			selectedID: -1,
			selectedTodo: null,
			todos: [],
		};
	},
	methods: {
		retrieveTodos() {
			TodoDataService.getAll()
				.then(response => {
					this.todos = response.data;
				})
				.catch(e => {
					console.log(e);
				});
		},
		selectTodo(todo, id) {
			this.selectedTodo = todo;
			this.selectedID = todo != null ? id : -1;
			this.selected = todo != null;
		},
		removeAllTodo() {
			TodoDataService.deleteAll()
				.then(response => {
					console.log(response.data);
					this.retrieveTodos();
					this.selected = false;
					this.selectedID = -1;
					this.selectedTodo = null;
				})
				.catch(e => {
					console.log(e);
				});
		},
		searchTodo() {
			TodoDataService.findByTitle(this.searchTitle)
				.then(response => {
					this.todos = response.data;
					this.selected = false;
					this.selectedID = -1;
					this.selectedTodo = null;
				})
				.catch(e => {
					console.log(e);
				});
		},
	},
	mounted() {
		this.retrieveTodos();
	}
};
</script>
