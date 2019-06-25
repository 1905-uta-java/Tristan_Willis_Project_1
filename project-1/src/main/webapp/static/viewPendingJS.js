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
		let baseUrl = "http://localhost:8080/project-1/api/pendreps?id=";
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
	let table = document.getElementById("Reports");
	console.log(info);
	for(let i of info){
		let row = table.insertRow(-1);
		let empCell = row.insertCell(0);
		let empText = document.createTextNode(i.employeeName);
		empCell.appendChild(empText);
		
		let amtCell = row.insertCell(1);
		let amtText = document.createTextNode(i.amount);
		amtCell.appendChild(amtText);
		
		let dscCell = row.insertCell(2);
		let dscText = document.createTextNode(i.desc);
		dscCell.appendChild(dscText);
		if(token.split(":")[0] != i.employeeID){
			let appCell = row.insertCell(3);
			let abtn = document.createElement('input');
			abtn.type = "button";
			abtn.className = "btn btn-primary";
			abtn.name = "" + i.reportID;
			abtn.value = "Approve";
			abtn.onclick = function(){
				approve(this.name);
			};
			appCell.appendChild(abtn);
			
			let denCell = row.insertCell(4);
			let dbtn = document.createElement('input');
			dbtn.type = "button";
			dbtn.className = "btn btn-primary";
			dbtn.name = "" + i.reportID;
			dbtn.value = "Deny";
			dbtn.onclick = function(){
				deny(this.name);
			};
			denCell.appendChild(dbtn);
		}
	}
}

function approve(but_id){
	let emp_id = token.split(":")[0];
	let baseUrl = "http://localhost:8080/project-1/api/acceptDenyRep?rep_id="+but_id+"&emp_id="+emp_id+"&accepted=TRUE";
	let xhr = new XMLHttpRequest();
	xhr.open("PUT", baseUrl);
	xhr.onreadystatechange = function(){
		if(this.readyState === 4 && this.status === 200){
			console.log("SUCCEEDED");
			location.reload();
		}
		else if(this.readyState === 4){
			console.log("FAILED");
		}
	}
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.setRequestHeader("Authorization", token);
	let requestBody = "";
	xhr.send(requestBody);
}

function deny(but_id){
	let emp_id = token.split(":")[0];
	let baseUrl = "http://localhost:8080/project-1/api/acceptDenyRep?rep_id="+but_id+"&emp_id="+emp_id+"&accepted=FALSE";
	let xhr = new XMLHttpRequest();
	xhr.open("PUT", baseUrl);
	xhr.onreadystatechange = function(){
		if(this.readyState === 4 && this.status === 200){
			console.log("SUCCEEDED");
			location.reload();
		}
		else if(this.readyState === 4){
			console.log("FAILED");
		}
	}
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.setRequestHeader("Authorization", token);
	let requestBody = "";
	xhr.send(requestBody);
}