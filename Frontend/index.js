
const HOST = 'http://localhost:8084/TallerJS/API';
/*
   function registrar(p1, p2){
      
   }
*/

const registrar = async () => {
   
   let nombre = document.getElementById('inputName').value;
   let apellido = document.getElementById('inputLastname').value;
   let email = document.getElementById('inputEmail').value;
   let passw = document.getElementById('inputPassword').value;
   let direccion = document.getElementById('inputAddress').value;
   let ciudad = document.getElementById('inputCity').value;
   let estado = document.getElementById('inputState').value;
   let codigoPostal = document.getElementById('inputZip').value;


   /*
      JSON key : value
   */

   const data_2 = { 
      'nombre': nombre, 
      'apellido': apellido  
   };

   const data = { nombre, apellido, email, passw, direccion, ciudad, estado, codigoPostal };

   if(!validation(nombre, apellido, email, passw, direccion, ciudad, estado, codigoPostal )){
      showMessage('Lo sentimos!!', 'No debe haber campos vacios', 'warning');
      return;
   }

   let response = await guardar(HOST, 'POST', data);

   if(response.status === 200){
      showMessage('Genial!!', response.msj, 'success');
      clear();
   }
   else{
      showMessage('Error', response.msj, 'error');
   }
}

const guardar = async (url, method, data) => {

   let response = await fetch(url, {
      method: method,
      body: JSON.stringify(data) 
   });

   if(!response.ok){
      throw new Error(`HTTP error: ${ response.status }`);
      //throw new Error('HTTP error: ' + response.status );
   }

   let resp = await response.json();
   return resp;

}

const validation = (nombre, apellido, email, passw, direccion, ciudad, estado, codigoPostal) => {

   if(nombre == '' || apellido == '' || email == '' || passw == '' || direccion == '' || ciudad == '' || estado == '' || codigoPostal == '')
      return false;

   return true;
}

const showMessage = (title, text, icon) => {
   Swal.fire({ icon, title, text });
}

const clear = () => {

   document.getElementById('inputName').value = '';
   document.getElementById('inputLastname').value = '';
   document.getElementById('inputEmail').value = '';
   document.getElementById('inputPassword').value = '';
   document.getElementById('inputAddress').value = '';
   document.getElementById('inputCity').value = '';
   document.getElementById('inputState').value = '';
   document.getElementById('inputZip').value = '';
}
