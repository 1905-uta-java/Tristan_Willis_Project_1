/**
 * 
 */
document.getElementById("LoginSubmit").addEventListener("click", requestLogin);
document.getElementById("CreateSubmit").addEventListener("click", createUser);

function requestLogin(){
	let url = "http://localhost:8080/project-1/login";
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.onreadystatechange = function(){
		if(this.readyState === 4 && this.status === 200){
			let auth = xhr.getResponseHeader("Authorization");
			sessionStorage.setItem("token", auth);
			window.location.href="http://localhost:8080/project-1/static/home.html";
		}
	}
	let user = document.getElementById("LoginUsername").value;
	let pass = document.getElementById("LoginPassword").value;
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	let requestBody = "LoginUsername="+user+"&LoginPassword="+pass;
	xhr.send(requestBody);
}

function createUser(){
	let url = "http://localhost:8080/project-1/newuser";
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.onreadystatechange = function(){
		if(this.readyState === 4 && this.status === 200){
			document.getElementById("Created").innerHTML = "Successfully created employee!";
		}
		else if(this.readyState === 4){
			document.getElementById("Created").innerHTML = "Failed to create employee.";
		}
	}
	let user = document.getElementById("CreateUsername").value;
	let pass = document.getElementById("CreatePassword").value;
	let cpass = document.getElementById("ConfirmPassword").value;
	let name = document.getElementById("SetFullName").value;
	let email = document.getElementById("SetEmail").value;
	
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	let requestBody = "CreateUsername="+user+"&CreatePassword="+pass+"&ConfirmPassword="+cpass+"&SetFullName="+name+"&SetEmail="+email;
	xhr.send(requestBody);
}