<#import "parts/common.ftl" as c>

<@c.page>

       <div class="form-group">
              <form action="/main" method="post">
                     <select class="custom-select col-md-6" id="typeSelect" name="productTypeId">
                            <#list productTypes! as productType>
                                   <option value="${productType.id}">${productType.name}</option>
                            </#list>
                     </select>
                     <input type="hidden" name="_csrf" value="${_csrf.token}" />
<#--                 <button type="submit"  class="btn btn-primary ml-2">Start parsing</button>-->
                     <!-- Button trigger modal -->
                     <button id="modalActivate" type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalPreview">
                            Start parsing
                     </button>
                     <!-- Modal -->
                     <div class="modal fade right" id="modalPreview" tabindex="-1" role="dialog" aria-labelledby="modalPreviewLabel" aria-hidden="true">
                            <div class="modal-dialog modal-sm" role="document">
                                   <div class="modal-content">
                                          <div class="modal-header">
                                                 <h5 class="modal-title" id="modalPreviewLabel">Sorry, can't parse</h5>
                                          </div>
                                          <div class="modal-body">
                                                  Your request is blocked by pleer.ru
                                          </div>
                                          <div class="modal-footer">
                                                 <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                                          </div>
                                   </div>
                            </div>
                     </div>
                     <!-- Modal -->
              </form>
       </div>

       <div class="form-row">
              <div class="form-group col-md-6">
                     <form method="get" action="/main" class="form-inline">
                            <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search by description">
                            <button type="submit" class="btn btn-primary ml-2">Search</button>
                     </form>
              </div>
       </div>

       <div class="card-columns">
              <#list products as product>
                     <div class="card my-3">
                            <div class="card-header text-muted">
                                   <span>Product ${product.code}<br /></span>
                                   <span>Price: ${product.price} RUB</span>
                            </div>

                            <#if product.filename??>
                                   <img src="/img/${product.filename}" class="card-img-top">
                            </#if>

                            <div class="card-body">
                                   <#if product.description??>
                                          <span>${product.description?jString}</span>
                                   </#if>
                            </div>

                            <div class="card-footer text-muted">
                                   <span>Added by: ${product.authorName}</span>
                            </div>
                     </div>
              <#else>
                     No product
              </#list>
       </div>

</@c.page>