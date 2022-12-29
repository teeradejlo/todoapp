import axios from '../axios';

class TodoDataService {
	getAll() {
		return axios.get("/todos");
	}

	get(id) {
		return axios.get(`/todos/${id}`);
	}

	create(data) {
		return axios.post("/todos", data);
	}

	update(id, data) {
		return axios.put(`/todos/${id}`, data);
	}

	delete(id) {
		return axios.delete(`/todos/${id}`);
	}

	deleteAll() {
		return axios.delete(`/todos`);
	}

	findByTitle(title) {
		return axios.get(`/todos?title=${title}`);
	}
}

export default new TodoDataService();