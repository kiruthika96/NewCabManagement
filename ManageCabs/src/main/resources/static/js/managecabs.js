
 												// fetching list of cab model and list out in the CabModel drop down
 
        var xhrCabModel = new XMLHttpRequest();
        var savingNewRecord = true; //boolean value to differentiate between saving a new record and editing an existing one
        

 

 document.getElementById("pills-managecabs-tab").addEventListener('click',function(){
 event.preventDefault();
  xhrCabModel.open("GET","http://localhost:8080/all/cabModel",true);
         
            xhrCabModel.onreadystatechange=cabModelProcessResponse;
            xhrCabModel.send(null);
    
 });
    
    var cabModels;
    
    function cabModelProcessResponse(){
            if(xhrCabModel.readyState == 4 && xhrCabModel.status == 200){
      var cabModelList= document.getElementById("cab-Model-Dropdown");
               
                var length = cabModelList.options.length;
             
              for (i = length-1; i > 0; i--) {
                   cabModelList.options[i] = null;
              }
              
                 cabModels = JSON.parse(xhrCabModel.responseText);
      
                for(var i=0; i<cabModels.length; i++){
        
                    var opt = document.createElement("option");
                    
                    opt.innerHTML = cabModels[i];
                      document.getElementById("cab-Model-Dropdown").options.add(opt);

                 }

              }
              
   // expiry date field           
  
	   var date=new Date();
	   var month = (date.getMonth()+1);
	   var day = date.getDate();
	   if(month<10)
	   {
	   	month = "0"+month;
	   }
	   if(day<10){
	   	day = "0"+day;
	   }
	   var currentDate=(date.getFullYear())+"-"+month+"-"+day;
	   
	   document.getElementById("ins-exp-date").setAttribute('min',currentDate);
 
        }
        
        
/* -----------------------------------------------------------------------------------------------------------------------------*/   

// driver name autocompleted 

var xhrDriverInfo = new XMLHttpRequest(); 

document.getElementById("pills-managecabs-tab").addEventListener('click',function(){
 event.preventDefault();
  xhrDriverInfo.open("GET","http://localhost:8080/all/driverInfo",true);
         
            xhrDriverInfo.onreadystatechange=driverInfoProcessResponse;
            xhrDriverInfo.send(null);
    
 });
 
 var driverDetails;
    
    function driverInfoProcessResponse(){
            if(xhrDriverInfo.readyState == 4 && xhrDriverInfo.status == 200){ 
 
        
  			driverDetails = JSON.parse(xhrDriverInfo.responseText);
			}
}



var drivNameInput = document.getElementById("driv-name");

//function call on entering values
drivNameInput.addEventListener("input", searchForDriverName);

var divElement = document.getElementById("suggestDiv");
var getDriverId;

function searchForDriverName() {
    
    var userEntry = drivNameInput.value;
   
    //delete old listtag if any 
    let childCount = divElement.childElementCount;
    if(childCount > 0) {
        for(let i=0; i<childCount; i++) {
            divElement.removeChild(divElement.firstChild);
        }
    }
    //

    if(userEntry != "") {
        //change user entry to lower case and compare with driver info array
        var suggestionArr = driverDetails.filter(text => text.driverName.toLowerCase().startsWith(userEntry.toLowerCase()));
        
        var suggLimit = 7;

        //creating list tag starts
        for(let i=0; i<suggestionArr.length; i++) {
             
            divElement.style.display = "block";

            //limit suggestion to 5
            if(i<suggLimit) {
                var listTag = document.createElement("li");
                listTag.id=suggestionArr[i].driverId;
                
                listTag.className = "suggestList";
                listTag.innerText = suggestionArr[i].driverName + " - " + suggestionArr[i].driverNumber 

                divElement.appendChild(listTag);
            }
        }
        //
    }
    
    //onclick function for list tags
    var suggestList = document.getElementsByClassName("suggestList");

    for(var i=0; i<suggestList.length; i++) {
        suggestList[i].addEventListener("click", function() {

            let driverDetails = this.innerText.split(" -");

            //append the value clicked to textbox capitalize first letter
            drivNameInput.value = driverDetails[0].charAt(0).toUpperCase() + driverDetails[0].slice(1);
			
			getDriverId = this.id;
            //hide list 
            divElement.style.display = "none";

        })
    }

}

document.addEventListener("click", function(e) {
    divElement.style.display = "none";
})


/* -----------------------------------------------------------------------------------------------------------------------------*/

															// Get all CabInfo and append into html page


 var xhrCabDetails = new XMLHttpRequest();

 

 document.getElementById("pills-managecabs-tab").addEventListener('click',function(){
 event.preventDefault();
  xhrCabDetails.open("GET","http://localhost:8080/all/cabInfo",true);
         
            xhrCabDetails.onreadystatechange=cabInfoProcessResponse;
            xhrCabDetails.send(null);
    
 });
 
    function cabInfoProcessResponse(){
  if(xhrCabDetails.readyState == 4 && xhrCabDetails.status == 200)
            {
             $("#cab-info").empty();
             var arr = JSON.parse(xhrCabDetails.responseText);
           
             var rowCounter = 0;
             
       for(var i=0;i<arr.length; i++){

 

       // creating row and data  
        
        var trow=document.createElement('tr');
        trow.className="row-bg-style";             // display-shadow       // addingStyle class
        trow.id = "tr" + rowCounter++;
        
        
        
        var divObj = document.createElement('td');
        divObj.className="spacing";
        divObj.id = "tdmodel" + i;
        
        var divObj1 = document.createElement('td');
        divObj1.className="spacing";
        divObj1.id = "tdcabnum" + i;
        
        var divObj2 = document.createElement('td');
        divObj2.className="spacing";
        divObj2.id = "tdseats" + i;
        
        var divObj3 = document.createElement('td');
        divObj3.className="spacing";
        divObj3.id = "tdinsnum" + i;
        
        var divObj4 = document.createElement('td');
        divObj4.className="spacing";
        divObj4.id = "tdexpdate" + i;
        
        var divObj5 = document.createElement('td');
        divObj5.className="spacing";
        divObj5.id = "tddrivename" + i;
        
        var divObj6 = document.createElement('td');
        divObj6.className="spacing text-center";
        divObj6.id = "tdeditdelete" + i;
        
        var divObj7 = document.createElement('td');
        divObj7.className="spacing";
        divObj7.id = "tdDid" + i;
       
                
        divObj.innerText = arr[i].cabModel;
        divObj1.innerText = arr[i].cabNumber;
        divObj2.innerText = arr[i].availableSeats;
        divObj3.innerText = arr[i].insuranceNumber;
        divObj4.innerText = formatDate(arr[i].expiryDate,1);
        divObj5.innerText = arr[i].driverName;
        divObj6.innerHTML="<a href='#' title='Edit' class='actions-image'><img src='images/edit.svg' alt='edit-icon' onclick='editData(this)'/></a><a href='#' title='Delete' class='actions2-image'><img src='images/delete.svg' onclick='deleteData(this)' alt='delete-icon' data-toggle='modal' data-target='#manage-pop' /></a>";
        divObj7.innerText=arr[i].driverId;
         
 		divObj7.style.display="none";
        
        trow.appendChild(divObj);
        trow.appendChild(divObj1);
        trow.appendChild(divObj2);
        trow.appendChild(divObj3);
        trow.appendChild(divObj4);
        trow.appendChild(divObj5);
        trow.appendChild(divObj6);
        trow.appendChild(divObj7);
       
     
        document.getElementById("cab-info").appendChild(trow);
  
  	}
  }
 } 
  
 /* ------------------------------------------------------------------------------------------------------------------------*/
 
 																	//Add Cab Model popup 
 
 
 var ddloption ;
 var capitalizedCabModel;
 
 document.getElementById("addCabModel").addEventListener('click',function()
 {
 event.preventDefault();
 
 
 var addCabModel= document.getElementById("model-pop2").value;
 
	capitalizedCabModel =addCabModel.toUpperCase().trim();
 
 
	 
	 if( capitalizedCabModel == "") 
	 {
	  alert("Enter model field cannot be empty");  
	  return false;  
	 } 
    
    
	ddloption =  document.getElementById("cab-Model-Dropdown").options;
	
	for(var i = 0; i < ddloption.length; i++)
	{    if( ddloption[i].value === capitalizedCabModel)
	    {   
	        alert("CabModel already exist ");
	        return false;    
l	    }    
	}    

	
  var option=document.createElement("option");
  option.innerText=capitalizedCabModel;
  
  document.getElementById("cab-Model-Dropdown").appendChild(option); 
 
  
 });


/* -------------------------------------------------------------------------------------------------------------------------*/


													// Save Cab Info to the database


 
 var xhrSaveCabDetails = new XMLHttpRequest();



 function validateCabDetails(){
 
 				if(document.getElementById('cab-Model-Dropdown').selectedIndex==0)
                {
                    alert("Please select the cab model");
                    return false;
                }
 				if(document.getElementById('cab-num').value == undefined || document.getElementById('cab-num').value =="")
                {
                    alert("Cab Number cannot be Empty");
                    return false;
                }
                if(document.getElementById('no-seats').value == undefined || document.getElementById('no-seats').value =="")
                {
                    alert("Available No.Of Seats cannot be Empty");
                    return false;
                }
                if(document.getElementById('insurance-num').value == undefined || document.getElementById('insurance-num').value =="")
                {
                    alert("Insurance number cannot be Empty");
                    return false;
                }
                if(document.getElementById('ins-exp-date').value == undefined || document.getElementById('ins-exp-date').value =="")
                {
                    alert("Expiry date cannot be Empty");
                    return false;
                }
                if(document.getElementById('driv-name').value == undefined || document.getElementById('driv-name').value =="")
                {
                    alert("Driver name cannot be Empty");
                    return false;
                }
           
                formatValidation();
 
  }
  
  function formatValidation(){
  
  const cabNumPattern= new RegExp("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$");
  var cabNumTrue=cabNumPattern.test(document.getElementById("cab-num").value);
  
  if(!cabNumTrue){
  alert("Cab Number pattern mismatch");
  return false;
  }
  
 if(document.getElementById("no-seats").value>=99){
  alert("Invalid Available number of seats");
  return false;
  }
  
  if(document.getElementById("insurance-num").value.length!=20){
  alert("Invalid Insurance Number");
  return false;
  }
    saveCabDetails();
  }
  
  
  
  function saveCabDetails(){
  
  var cabModel=document.getElementById("cab-Model-Dropdown").value;
  var cabNumber=document.getElementById("cab-num").value.trim();
  var availableSeats=document.getElementById("no-seats").value.trim();
  var insuranceNum=document.getElementById("insurance-num").value.trim();
  var expDate=document.getElementById("ins-exp-date").value;
  var driverName=document.getElementById("driv-name").value.trim();
   
  
  
  
  
  if(savingNewRecord)
  {
	var driverId=getDriverId;
	if(driverId==undefined)
	{
		alert("Please select the driver name from the shown list");
		return false;
	}
	var data = {"cabModel":cabModel,"cabNumber":cabNumber,"availableSeats":availableSeats,
   "insuranceNumber":insuranceNum,"expiryDate":expDate,"driverName":driverName,"driverId":driverId};
   
    xhrSaveCabDetails.open("POST","http://localhost:8080/addCabInfo",true);
    }
    
 else{ //for updating existing data
     
     driverId=getUpDriverId;
 if(driverId==driverId && driverName!=driver || driverId!=driverId && driverName==driver)
  {
	alert("Please select the driver name from the shown list");
	return false;	
  }
  else if(driverId==getDriverId){
 var data = {"cabModel":cabModel,"cabNumber":cabNumber,"availableSeats":availableSeats,
   "insuranceNumber":insuranceNum,"expiryDate":expDate,"driverName":driverName,"driverId":driverId};
   
 	 xhrSaveCabDetails.open("PUT","http://localhost:8080/updateCabInfo",true);}
 	}
 	
 	xhrSaveCabDetails.setRequestHeader("Content-Type","application/json");
 	xhrSaveCabDetails.send(JSON.stringify(data));
  
   if(savingNewRecord)
    xhrSaveCabDetails.onreadystatechange=saveCabInfoProcessResponse;  
    else
    xhrSaveCabDetails.onreadystatechange=updateCabInfoProcessResponse;
 
 }
 
 function saveCabInfoProcessResponse()
 {
 
 //created
 
 if (xhrSaveCabDetails.readyState == 4 &&  xhrSaveCabDetails.status == 201) {
   
     var response = this.responseText;
     alert("Cab Details saved successfully");
     funclear();
   }
   
   //NOT_ACCEPTABLE
   
   if(xhrSaveCabDetails.readyState == 4 &&  xhrSaveCabDetails.status == 406){
   alert("Cab number already exist");
   } 
   
   //FOUND
   
   if(xhrSaveCabDetails.readyState == 4 &&  xhrSaveCabDetails.status == 302){
   alert("Driver already assigned to a cab");
   } 
   
 //  location.reload();
   
 }


/* -----------------------------------------------------------------------------------------------------------------------*/


									//Cancel button -> clear the data which was entered by the admin


function funclear()
            {
            	savingNewRecord = true;
                document.getElementById("cab-Model-Dropdown").value="";
                Reset();
                document.getElementById("cab-num").value="";
                //document.getElementById("cab-num").disabled="true";
                document.getElementById("no-seats").value="";
                document.getElementById("insurance-num").value="";
                document.getElementById("ins-exp-date").value=true;
                document.getElementById("driv-name").value="";
                
               // location.reload();
            }

function Reset() {
        var dropDown = document.getElementById("cab-Model-Dropdown");
        dropDown.selectedIndex = 0;
    }
    
    
    
/* --------------------------------------------------------------------------------------------------------*/

 
														 // Delete cab Details
 
 var xhrDeleteCabDetails = new XMLHttpRequest();
 var delCab;

var delId;
 var delrow;
  function deleteCabDetails(){
  
 
  
    xhrDeleteCabDetails.open("PUT","http://localhost:8080/deleteCabInfo/"+delCab,true);
 	
 	
 	xhrDeleteCabDetails.setRequestHeader("Content-Type","application/json");
 	xhrDeleteCabDetails.send(null);
  
    xhrDeleteCabDetails.onreadystatechange=deleteCabInfoProcessResponse;  
 
 }
 
 function deleteCabInfoProcessResponse()
 {
 if (xhrDeleteCabDetails.readyState == 4 &&  xhrDeleteCabDetails.status == 200) {
   
     var response = this.responseText;
     //alert(delrow);
     delrow.remove(); //tr0
     alert("Cab Details deleted successfully");
   }
  // location.reload();
 }

function deleteData(row){
	delId = row.closest("td").id;
	//alert(delId); tdeditdelete0
	var counter=delId.replace("tdeditdelete","");
	//alert(counter);
              delrow =document.getElementById("tr"+counter);  //tr0
              
                delCab = delrow.getElementsByTagName("td")[1].innerHTML;
              //  alert(delCab);
                
                
}



/* --------------------------------------------------------------------------------------------------------*/
var editId;
var editRow;
var driverId;
var driver;
var getUpdriverId;
function editData(row){
    savingNewRecord = false;
	

	editId = row.closest("td").id;
	//alert(editId);
	var counterEdit=editId.replace("tdeditdelete","");
	editRow =document.getElementById("tr"+counterEdit);
	
	var model = editRow.getElementsByTagName("td")[0].innerHTML;
	var numberCab = editRow.getElementsByTagName("td")[1].innerHTML;
	var seatsAvailable = editRow.getElementsByTagName("td")[2].innerHTML;
	var ins = editRow.getElementsByTagName("td")[3].innerHTML;
	var exp = editRow.getElementsByTagName("td")[4].innerHTML;
	driver = editRow.getElementsByTagName("td")[5].innerHTML;
	//driverId = editRow.getElementsByTagName("td")[7].innerHTML;
	getUpDriverId=document.getElementById("tdDid"+counterEdit).innerHTML;

	
	document.getElementById("cab-Model-Dropdown").value = model;
	document.getElementById("cab-num").value = numberCab;
	
	document.getElementById("cab-num").disabled = "true";
	
	document.getElementById("no-seats").value = seatsAvailable;
	document.getElementById("insurance-num").value = ins;
	document.getElementById("ins-exp-date").value = formatDate(exp,1);   //does not required format yyyy/mm/dd
	document.getElementById("driv-name").value = driver;
	//getDriverId=driverId;

}
 
 
 
  
 // var xhrUpdate = new XMLHttpRequest();

 
 function updateCabInfoProcessResponse()
 {
 
 // ACCEPTED
 
 if (xhrSaveCabDetails.readyState == 4 &&  xhrSaveCabDetails.status == 202) {
   
     var response = this.responseText;
     
	
    alert("Cab Details updated successfully");    
    funclear();
   }
   
   //FOUND
   
   if (xhrSaveCabDetails.readyState == 4 &&  xhrSaveCabDetails.status == 302) {
   
   alert("Driver already exist");
   
   }
    
   //location.reload();
  
}


/* ------------------------------------------------------------------------------------------------------------------------------------------------------*/


// date formate change

function formatDate(date, option){
	var arr = date.split("-");
	
	if(option ==1)
     var formatedDate = arr[2] + "-" + arr[1] + "-" + arr[0];
     else if(option==2) //dd-mm-yyyy
     var formatedDate = arr[1] + "-" + arr[0] + "-" + arr[2];
  return formatedDate;
	
}


/* -------------------------------------------------------------------------------------------------------------------*/

