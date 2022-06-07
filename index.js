/*
   function registrar(p1, p2){
      
   }
*/

const registrar = () => {
   
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

   

}

const validation = (nombre, apellido, email, passw, direccion, ciudad, estado, codigoPostal) => {

   if(nombre == '' || apellido == '' || email == '' || passw == '' || direccion == '' || ciudad == '' || estado == '' || codigoPostal == '')
      return false;

   return true;
}

const showMessage = (title, text, icon) => {
   Swal.fire({ icon, title, text });
}