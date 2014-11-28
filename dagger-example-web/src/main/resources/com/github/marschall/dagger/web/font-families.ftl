<#import "dagger-page.ftl" as lib>
<@lib.dagger_page>
  <h1>Font Families</h1>
  <ul>
  <#list fontFamilies as fontFamily>
    <li>${fontFamily}</li>
  </#list>
  </ul>
</@lib.dagger_page>