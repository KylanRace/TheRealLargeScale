
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<table width="75%" border="0" cellpadding="0" cellspacing="0">
  <tr bgcolor="#FF0000"> 
    <td height="42"><div align="center"><font color="#FFFFFF"><strong><a href="directions.jsp">Get 
        Directions</a></strong></font></div></td>
    <td><div align="center"><font color="#FFFFFF"><strong><a href="resources.jsp">Find 
        Resources</a></strong></font></div></td>
    <td><div align="center"><font color="#FFFFFF"><strong><a href="showBuildings.jsp">Show 
        Buildings</a></strong></font></div></td>
    <td><div align="center"><font color="#FFFFFF"><strong><a href="showResources.jsp">Show 
        Campus Resources</a></strong></font></div></td>
    <td><div align="center"><font color="#FFFFFF"><strong><a href="showDepartments.jsp">Show 
        Departments</a></strong></font></div></td>
  </tr>
  <tr bgcolor="#FFFFFF"> 
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FF0000">&nbsp;</td>
    <td>&nbsp;</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr bgcolor="#000099"> 
    <td colspan="5" bordercolor="0"><div align="center"><strong><font color="#FFFFFF" size="6">Find 
        Campus Resource</font></strong></div></td>
  </tr>
  <tr bgcolor="#000099"> 
    <td height="252" colspan="5" bordercolor="0"><div align="center">
        <table width="75%" border="1">
          <tr>
            <td><font color="#FFFFFF"><strong>Starting Location (Building): </strong></font></td>
            <td><form name="form1" method="post" action="">
                <select name="select">
                  <option value="0">Drew Hall</option>
                  <option value="10">Burr Gym</option>
                  <option value="18">Cook Hall</option>
                  <option value="19">Greene Memorial Stadium</option>
                  <option value="21">Howard Manor</option>
                  <option value="24">Effingham Apartments</option>
                  <option value="31">School of Business</option>
                  <option value="35">Crampton Auditorium</option>
                  <option value="37">Douglass Hall (Back Door)</option>
                  <option value="41">Douglass Hall (North Door)</option>
                  <option value="44">Miner Hall</option>
                  <option value="46">Mordecai Johnson Building (Administration)</option>
                  <option value="49">Douglass Hall (South Door)</option>
                  <option value="52">Douglass Hall (Front Door)</option>
                  <option value="54">Ira Aldridge Theatre</option>
                  <option value="56">Childers Hall (Fine Arts)</option>
                  <option value="70">Carnegie Hall</option>
                  <option value="76">Blackburn Center</option>
                  <option value="80">Locke Hall</option>
                  <option value="85">School of Education</option>
                  <option value="87">Center for Academic Reinforcement</option>
                  <option value="93">Carnegie Hall</option>
                  <option value="97">Lindsay Hall (Social Work)</option>
                  <option value="99">Howard Hall</option>
                  <option value="106">Mackey Building (Front Door)</option>
                  <option value="108">Mackey Building (Back Door)</option>
                  <option value="119">Downing Hall (Engineering)</option>
                  <option value="123">Thirkield Hall (Physics)</option>
                  <option value="127">Rankin Chapel</option>
                  <option value="134">Founders Library-MSRC (Back Door)</option>
                  <option value="136">Founders Library-MSRC (Front Door)</option>
                  <option value="143">Chemistry Building (Front Door)</option>
                  <option value="147">Chemistry Building (Back Door)</option>
                  <option value="150">Just Hall (Front Door)</option>
                  <option value="153">Just Hall (Side Door)</option>
                  <option value="156">Just Hall (Back Door)</option>
                  <option value="159">School of Pharmacy</option>
                  <option value="164">Founders Library-UGL</option>
                  <option value="171">School of Pharmacy</option>
                  <option value="175">Tubman Quadrangle (Front Door)</option>
                  <option value="178">Wheatley Hall</option>
                  <option value="179">Truth Hall</option>
                  <option value="181">Crandall Hall</option>
                  <option value="184">Frazier Hall</option>
                  <option value="187">Baldwin Hall</option>
                  <option value="189">Tubman Quadrangle (Back Door)</option>
                  <option value="193">Bethune Annex (Back Door)</option>
                  <option value="196">Bethune Annex (Front Door)</option>
                  <option value="204">Mental Health Center</option>
                  <option value="206">Laser Chemistry Building</option>
                  <option value="209">Graduate School</option>
                  <option value="212">WHUT-TV</option>
                  <option value="220">CB Powell Building (Communications)</option>
                  <option value="225">WHUR-Radio</option>
                  <option value="235">Power Plant</option>
                  <option value="236">Ralph J Bunche Center</option>
                  <option value="241">Downing Hall (Engineering)</option>
                  <option value="243">ISAS</option>
                  <option value="245">ILAB</option>
                  <option value="249">Howard Center/Bookstore</option>
                  <option value="253">Student Health Center</option>
                  <option value="258">School of Nursing & Aliied Health</option>
                  <option value="267">Health Science Library</option>
                  <option value="-999" selected>None</option>
                </select>
              </form></td>
            <td><font color="#FFFFFF"><strong>Find Resource: </strong></font></td>
            <td><form name="form2" method="post" action="">
                <select name="select2">
                  <option value="23">ATM</option>
                  <option value="1">Vending Machine</option>
                  <option value="2">Rest Rooms</option>
                  <option value="3">Public Phone</option>
                  <option value="4">Computer Lab</option>
                  <option value="5">WiFi Hotspot</option>
                  <option value="6">Security</option>
                  <option value="7">Bus</option>
                  <option value="-999" selected>None</option>
                </select>
              </form></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td><form name="form3" method="post" action="http://fathomless-shelf-5069.herokuapp.com/ResourceFinder">
                <input type="submit" name="Submit" value="Submit">
              </form></td>
          </tr>
        </table>
      </div></td>
  </tr>
</table>
</body>
</html>
