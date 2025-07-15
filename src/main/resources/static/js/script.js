console.log("This is script tag")

function toggleSidebar() {
  const sidebar = document.querySelector('.sidebar');
  const content = document.querySelector('.content');

  const isSidebarVisible = getComputedStyle(sidebar).display !== 'none';

  if (isSidebarVisible) {
    sidebar.classList.add('sidebar-hidden');
    content.classList.add('content-expanded');
  } else {
    sidebar.classList.remove('sidebar-hidden');
    content.classList.remove('content-expanded');
  }
}

const search = () => {
  const query = document.getElementById("search-input").value;
  const results = document.getElementById("search-result");
  if(query=="" || query.length<3){
    results.style.display = "none";
  }
  else{
    
    let url = `http://localhost:8080/search/${query}`;
    fetch(url)
      .then((response) =>{
        return response.json();
      })
      .then((data) => {
        console.log(data);
        let text = `<div class='list-group'>`;
        data.forEach((contact) =>{
          text += `<a href='/user/${contact.cId}/contact' class='list-group-item list-group-item-action' > ${contact.name} </a> `
        });
        text += `</div>`;
        results.innerHTML = text;
        results.style.display = "block";
      })
      
  }
}