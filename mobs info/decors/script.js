function getLoading() {
    var loadz = document.getElementById("preload");
    window.addEventListener("load", function() {
        loadz.style.display = "none";
    });
}

function turnBlindMode() {
    var checkBox = document.getElementById("blindmode"),
        blindImg = document.getElementById("blackImg");

    if (checkBox.checked)
    {
        blindImg.style.display = "flex";
    }
    else blindImg.style.display = "none";
}

function hide(ids) {
    document.getElementById(ids + '_b').disabled = false;
    var toHide = document.getElementById(ids);
    toHide.style.display = "none";
}

function display(id) {    
    document.getElementById(id + '_b').disabled = true;
    var toDisplay = document.getElementById(id);
    toDisplay.style.display = "inline";
    toDisplay.style.opacity = 1;
    toDisplay.style.visibility = "visible";

    let idSet = ['menu', 'akg_intro'];
    idSet.splice(idSet.indexOf(id), 1);

    idSet.forEach((div) => hide(div));
}

function find(e) {
  // Get the filter value
  let filter = e.target.value.toUpperCase();

  // Get the table rows
  let table = document.getElementById('dmg-chart2');
  let tr = table.getElementsByTagName('tr');

  // Loop through all table rows
  for (let i = 0; i < tr.length; i++) {
    // Get the text value of the first td in the row
    let td = tr[i].getElementsByTagName('td')[0];
    if (td) {
      let txtValue = td.textContent || td.innerText;
      // If the row text matches the filter, show the row, otherwise hide it
      if (txtValue.toUpperCase().indexOf(filter) > -1 && (!eliteOnly || (eliteOnly && td.className == "elite"))) {
        tr[i].style.display = 'table-row';
      } else {
        tr[i].style.display = 'none';
      }
    }
  }
};

function sortTable(type, value) {
  var table, rows, switching, i, x, y, shouldSwitch;
  table = document.getElementById("dmg-chart2");
  switching = true;
  
  while (switching) {
    switching = false;
    rows = table.rows;
    
    for (i = 1; i < (rows.length - 1); i++) {
      shouldSwitch = false;
      x = rows[i].getElementsByTagName("td")[type];
      y = rows[i + 1].getElementsByTagName("td")[type];
      
      if (value == true) {
        if (Number(x.innerHTML) > Number(y.innerHTML)) {
          shouldSwitch = true;
          break;
        }
      }
      else {
        if (Number(x.innerHTML) < Number(y.innerHTML)) {
          shouldSwitch = true;
          break;
        }
      }
    }
    
    if (shouldSwitch) {
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
    }
  }
}

var eliteOnly = false;

function eliteFilter() {
  eliteOnly = !eliteOnly;

  var table, rows, i, button;
    button = document.getElementById("filter");
    table = document.getElementById("dmg-chart2");
    rows = table.rows;

    if (eliteOnly) {
      button.innerText = "Show all";
      for (i = 1; i < rows.length; i++) {
          var current = rows[i].getElementsByTagName('td')[0];
          if (current.className != "elite") {
              rows[i].style.display = "none";
          }
          else {
              rows[i].style.display = "table-row";
          }
      }
    }
    else {
      button.innerText = "Elites only";
      for (i = 1; i < rows.length; i++) {
          rows[i].style.display = "table-row";
      }
    }
}