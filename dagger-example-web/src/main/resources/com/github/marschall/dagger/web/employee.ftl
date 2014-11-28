<#import "dagger-page.ftl" as lib>
<@lib.dagger_page>
  <h1>Employee</h1>
  <dl>
    <dt>Employee Id</dt><dd>${employee.employeeId}</dd>
    <dt>First Name</dt><dd>${employee.firstName}</dd>
    <dt>Last Name</dt><dd>${employee.lastName}</dd>
    <dt>Date Of Birth</dt><dd>${employee.dateOfBirth}</dd>
    <dt>Phone Number</dt><dd>${employee.phoneNumber}</dd>
  </dl>
</@lib.dagger_page>