/**
 * 
 */
let token = sessionStorage.getItem("token");
if(!token){
    window.location.href = "http://localhost:8080/project-1/static/index.html";
}
else {
	let tokenArr = token.split(":");
	if(tokenArr.length==2){
		let baseUrl = "http://localhost:8080/project-1/api/emps?id=";
		getInfo(baseUrl+tokenArr[0], displayInfo);
	}
	else{
	    window.location.href = "http://localhost:8080/project-1/static/index.html";
	}
}

document.getElementById("SetSubmit").addEventListener("click", updateUser);
document.getElementById("LogOut").addEventListener("click", logOut);

function getInfo(url, callback){
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.onreadystatechange = function(){
		if(this.readyState===4 && this.status===200){
			callback(this);
		}
	}
	xhr.setRequestHeader("Authorization", token);
	xhr.send();
}

function displayInfo(xhr){
	let info = JSON.parse(xhr.response);
	document.getElementById("SetUsername").value = info.login;
	document.getElementById("SetFullName").value = info.fullName;
	document.getElementById("SetEmail").value = info.email;
}

function updateUser(){
	let user = document.getElementById("SetUsername").value;
	let pass = document.getElementById("SetPassword").value;
	let cpass = document.getElementById("OldPassword").value;
	let name = document.getElementById("SetFullName").value;
	let email = document.getElementById("SetEmail").value;
	let url = "http://localhost:8080/project-1/api/updateEmp?"+"id="+token.split(":")[0]+"&SetUsername="+user+"&SetPassword="+pass+"&OldPassword="+cpass+"&SetFullName="+name+"&SetEmail="+email;
	let xhr = new XMLHttpRequest();
	xhr.open("PUT", url);
	xhr.onreadystatechange = function(){
		if(this.readyState === 4 && this.status === 200){
			document.getElementById("Reset").innerHTML = "Successfully updated employee!";
			let auth = xhr.getResponseHeader("Authorization");
			sessionStorage.setItem("token", auth);
		}
		else if(this.readyState === 4){
			document.getElementById("Reset").innerHTML = "Failed to update employee.";
		}
	}
	
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.setRequestHeader("Authorization", token);
	let requestBody = "";
	xhr.send(requestBody);
}

function logOut(){
	sessionStorage.removeItem("token");
	window.location.href = "http://localhost:8080/project-1/static/index.html";
}