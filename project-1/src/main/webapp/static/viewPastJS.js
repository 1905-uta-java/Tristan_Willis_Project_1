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
		let baseUrl = "http://localhost:8080/project-1/api/pastreps?id=";
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
		
		if(i.isAccepted == 1){
			let appCell = row.insertCell(3);
			let appText = document.createTextNode("Approved!");
			appCell.appendChild(appText);
		}
		else{
			let denCell = row.insertCell(3);
			let denText = document.createTextNode("Denied.");
			denCell.appendChild(denText);
		}
		
		let whoCell = row.insertCell(4);
		let whoText = document.createTextNode(i.resolvedName);
		whoCell.appendChild(whoText);
	}
}