/**
 * 
 */
let token = sessionStorage.getItem("token");
if(!token){
    window.location.href = "http://localhost:8080/project-1/static/index.html";
}
else {
	let tokenArr = token.split(":");
	if(tokenArr.length!=2){
	    window.location.href = "http://localhost:8080/project-1/static/index.html";
	}
}

document.getElementById("NewSubmit").addEventListener("click", newReport);

function newReport(){
	let amount = document.getElementById("SetAmount").value;
	let descript = document.getElementById("SetDescript").value;
	let url = "http://localhost:8080/project-1/createReport";
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.onreadystatechange = function(){
		if(this.readyState === 4 && this.status === 200){
			document.getElementById("Result").innerHTML = "Successfully created report!";
		}
		else if(this.readyState === 4){
			document.getElementById("Result").innerHTML = "Failed to create report.";
		}
	}
	
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.setRequestHeader("Authorization", token);
	let requestBody = "Amount="+amount+"&Descript="+descript;
	xhr.send(requestBody);
}