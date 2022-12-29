<template>
	<div class="page-body" style="background-color: rgb(208, 248, 255);">
		<div class="rounded-box fade-in">
			<div v-if="todo == null">
				<h2>Todo Not Found...</h2>
				<div class="center-container">
					<button class="btn btn-success" @click="$router.push('/todos/')">Back to Main Menu</button>
				</div>
			</div>
			<div v-else-if="!actions.published && !actions.deleted && !actions.updated">
				<h2 align="center" style="margin-bottom: 30px;">Todo</h2>
				<div class="form-group">
					<label for="title">Title</label>
					<input type="text" class="form-control" id="title" v-model="this.todoTitle" name="title" required />
				</div>
				<div class="form-group">
					<label for="description">Description</label>
					<input type="text" class="form-control" id="description" v-model="this.todoDescription"
						name="description" required />
				</div>
				<div class="form-group">
					<label><strong>Status:</strong></label> {{ todoStatus ? "Published" : "Pending" }}
				</div>
				<div class="mt-4">
					<button class="btn btn-primary mr-2 pl-3 pr-3" @click="publishTodo">Publish</button>
					<button class="btn btn-danger pl-3 pr-3" @click="deleteTodo">Delete</button>
					<button class="btn btn-success ml-2 pl-3 pr-3" @click="updateTodo">Update</button>
				</div>
			</div>
			<div v-else>
				<h2 v-if="this.actions.published">You published successfully!</h2>
				<h2 v-if="this.actions.deleted">You deleted successfully!</h2>
				<h2 v-if="this.actions.updated">You updated successfully!</h2>
				<div class="center-container">
					<button class="btn btn-success" @click="$router.push('/todos/')">Back to Main Menu</button>
				</div>
			</div>
		</div>
	</div>
</template>

<style>
.center-container {
	display: flex;
	justify-content: center;
	margin-top: 10%;
}
</style>

<script>
import TodoDataService from "../services/TodoDataService";
import "./common-styles.css";

export default {
	name: "todo-details",
	data() {
		return {
			actions: {
				published: false,
				deleted: false,
				updated: false,
			},
			todo: null,
			todoTitle: "",
			todoDescription: "",
			todoStatus: false,
		};
	},
	methods: {
		publishTodo() {
			TodoDataService.update(this.todo.id, { title: this.todo.title, description: this.todo.description, published: true })
				.then(() => {
					this.actions.published = true;
				})
				.catch(e => {
					console.log(e);
				});
		},
		deleteTodo() {
			TodoDataService.delete(this.todo.id)
				.then(() => {
					this.actions.deleted = true;
				})
				.catch(e => {
					console.log(e);
				});
		},
		updateTodo() {
			TodoDataService.update(this.todo.id, { title: this.todoTitle, description: this.todoDescription, published: this.todo.published })
				.then(() => {
					this.actions.updated = true;
				})
				.catch(e => {
					console.log(e);
				});
		}
	},
	mounted() {
		TodoDataService.get(this.$route.params.id)
			.then(response => {
				this.todo = response.data;
				this.todoTitle = this.todo.title;
				this.todoDescription = this.todo.description;
				this.todoStatus = this.todo.published;
			})
			.catch(e => {
				console.log(e);
			});
	}
};
</script>