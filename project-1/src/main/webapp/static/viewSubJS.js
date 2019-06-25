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
		let baseUrl = "http://localhost:8080/project-1/api/subs?id=";
		getInfo(baseUrl+tokenArr[0], displayInfo);
	}
	else{
	    window.location.href = "http://localhost:8080/project-1/static/index.html";
	}
}

function getInfo(url, callback){
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.onreadystatechange = function(){
		if(this.readyState===4 && this.status===200){
			callback(this);
		}
		else{
			console.log("FAILED");
		}
	}
	xhr.setRequestHeader("Authorization", token);
	xhr.send();
}

function displayInfo(xhr){
	let info = JSON.parse(xhr.response);
	let table = document.getElementById("Subs");
	console.log(info);
	for(let i of info){
		let row = table.insertRow(-1);
		
		let unmCell = row.insertCell(0);
		let unmText = document.createTextNode(i.login);
		unmCell.appendChild(unmText);
		
		let fnmCell = row.insertCell(1);
		let fnmText = document.createTextNode(i.fullName);
		fnmCell.appendChild(fnmText);
		
		let emlCell = row.insertCell(2);
		let emlText = document.createTextNode(i.email);
		emlCell.appendChild(emlText);
		
		let selCell = row.insertCell(3);
		let abtn = document.createElement('input');
		abtn.type = "button";
		abtn.className = "btn btn-primary";
		abtn.name = "" + i.employeeID;
		abtn.value = "View Reports";
		abtn.onclick = function(){
			select(this.name);
		};
		selCell.appendChild(abtn);
	}
}

function select(id){
	sessionStorage.setItem("sub_id", id);
	window.location.href="http://localhost:8080/project-1/static/viewSubReps.html"
}