<script>
import TodoDataService from "../services/TodoDataService";
import "./common-styles.css";

export default {
	name: "add-todo",
	data() {
		return {
			addingTodo: {
				id: null,
				title: "",
				description: "",
				published: false
			},
			submitted: false
		};
	},
	methods: {
		saveTodo() {
			var data = {
				title: this.addingTodo.title,
				description: this.addingTodo.description
			};

			TodoDataService.create(data)
				.then(response => {
					this.addingTodo.id = response.data.id;
					console.log(response.data);
					this.submitted = true;
				})
				.catch(e => {
					console.log(e);
				});
		},

		newTodo() {
			this.submitted = false;
			this.addingTodo = {};
		}
	}
};
</script>

<template>
	<div class="page-body" style="background-color: rgb(249, 255, 188);">
		<div class="rounded-box fade-in">
			<div v-if="!submitted">
				<h2 align="center" style="margin-bottom: 30px;">Add Todo</h2>
				<form @submit.prevent="saveTodo">
					<div class="form-group">
						<label for="title">Title</label>
						<input type="text" class="form-control" id="title" v-model="addingTodo.title" name="title"
							required />
					</div>
					<div class="form-group">
						<label for="description">Description</label>
						<input type="text" class="form-control" id="description" v-model="addingTodo.description"
							name="description" required />
					</div>
					<div class="center-container">
						<button class="btn btn-success" type="submit">Submit</button>
					</div>
				</form>
			</div>

			<div v-else>
				<h2>You submitted successfully!</h2>
				<div class="center-container">
					<button class="btn btn-success" @click="newTodo">Add New Todo</button>
				</div>
			</div>
		</div>
	</div>
</template >

<style>
.center-container {
	display: flex;
	justify-content: center;
	margin-top: 10%;
}
</style>
