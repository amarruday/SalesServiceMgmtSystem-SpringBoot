package com.yashsales.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.entity.Brand;
import com.yashsales.entity.Product;
import com.yashsales.entity.ProductCatagory;
import com.yashsales.entity.ProductType;
import com.yashsales.entity.User;
import com.yashsales.entity.UserProductCatagoryLink;
import com.yashsales.outputbeans.ProductBean;
import com.yashsales.outputbeans.ProductTypeOutputBean;
import com.yashsales.outputbeans.SearchProductPagniationOutputbean;
import com.yashsales.repository.BrandsRepository;
import com.yashsales.repository.ProductCatagoryRepository;
import com.yashsales.repository.ProductRepository;
import com.yashsales.repository.ProductTypeRepository;
import com.yashsales.repository.UserProductCatagoryLinkRepository;
import com.yashsales.repository.UserRepository;
import com.yashsales.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductTypeRepository productTypeRepo;

	@Autowired
	private ProductCatagoryRepository prodCatagoryRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private BrandsRepository brandRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserProductCatagoryLinkRepository userProductCatagoryLinkRepo;

	@Override
	public Map<String, Object> getProductTypes(Long productCatagoryId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<ProductType> productTypesList = null;
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		if (productCatagoryId > 0) {
			productTypesList = productTypeRepo.findAllByProductCatagory(productCatagoryId);
			returnMap.put("ProductTypesList", null);
			if (productTypesList.size() > 0 && productTypesList != null) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				returnMap.put("ProductTypesList", productTypesList);
			}
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getProductTypes() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<ProductType> productTypesList = null;
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		returnMap.put("ProductTypesList", null);

		productTypesList = productTypeRepo.findAll();
		returnMap.put("ProductTypesList", productTypesList);

		return returnMap;
	}

	@Override
	public Map<String, Object> getProductTypeByProductTypeId(Long productTypeId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		returnMap.put("message", "No Product type availabe for this id");

		if (productTypeId > 0) {
			ProductType productType = productTypeRepo.findById(productTypeId).orElse(null);
			if (productType != null && productType.getProductTypeId() > 0) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				returnMap.put("message", "");
				returnMap.put("ProductType", productType);
			}
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> addProductType(ProductTypeOutputBean productTypeBean) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		returnMap.put("message", "");
		if (productTypeBean.getProductCatagoryId() > 0) {
			List<ProductType> prodTypeList = null;
			prodTypeList = productTypeRepo.findAll();

			Optional<ProductType> duplicateProductName = prodTypeList.stream()
					.filter(pt -> pt.getProductName().trim().equalsIgnoreCase(productTypeBean.getProductName()))
					.findFirst();

			Optional<ProductType> duplicateTicketPrefix = prodTypeList.stream()
					.filter(pt -> pt.getTicketPrefix().trim().equalsIgnoreCase(productTypeBean.getTicketPrefix()))
					.findFirst();

			if (duplicateProductName.isPresent()) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
				returnMap.put("message", "This product type already exists!");
				returnMap.put("errorCode", "WSEM006");
			} else if (duplicateTicketPrefix.isPresent()) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
				returnMap.put("message", "This ticket prefix already exists!");
				returnMap.put("errorCode", "WSEM007");
			} else if (duplicateProductName.isPresent() && duplicateTicketPrefix.isPresent()) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
				returnMap.put("message", "This Product Name and ticket prefix already exists!");
				returnMap.put("errorCode", "WSEM008");
			} else {
				ProductCatagory prodCatagory = prodCatagoryRepo.findById(productTypeBean.getProductCatagoryId())
						.orElse(null);
				if (prodCatagory.getProductCatagoryId() > 0) {
					ProductType productType = new ProductType();
					productType.setProductCatagory(prodCatagory);
					productType.setProductName(productTypeBean.getProductName().trim());
					productType.setTicketPrefix(productTypeBean.getTicketPrefix());
					productType.setStatus(false);// By default Inactive
					productTypeRepo.save(productType);
					returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
					returnMap.put("message", "Product Type added successfully!");
					returnMap.put("errorCode", "");
				}
			}
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> updateProductType(ProductTypeOutputBean productTypeBean) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ProductType theProductType = null;
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		returnMap.put("message", "");

		if (productTypeBean.getProductTypeId() > 0) {
			theProductType = productTypeRepo.findById(productTypeBean.getProductTypeId()).get();
			List<ProductType> productTypeList = productTypeRepo.findAll();

			boolean duplicateFlag = false;
			// find if there is another Product Type exist or not
			for (int i = 0; i < productTypeList.size(); i++) {
				if (productTypeList.get(i).getProductName().trim()
						.equalsIgnoreCase(productTypeBean.getProductName().trim())) {
					if (productTypeList.get(i).getProductTypeId().equals(productTypeBean.getProductTypeId())) {
						duplicateFlag = false;
					} else {
						duplicateFlag = true;
					}
				}
			}

			if (duplicateFlag) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
				returnMap.put("message", "This Product Type is already available.");
				returnMap.put("errorCode", "WSEM0005");
			} else {

				if (!theProductType.getProductCatagory().getProductCatagoryId()
						.equals(productTypeBean.getProductCatagoryId())) {
					ProductCatagory pc = prodCatagoryRepo.findById(productTypeBean.getProductCatagoryId()).get();
					theProductType.setProductCatagory(pc);
				}

				theProductType.setProductName(productTypeBean.getProductName());
				theProductType.setTicketPrefix(productTypeBean.getTicketPrefix());
				theProductType.setStatus(productTypeBean.isStatus());
				productTypeRepo.save(theProductType);
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				returnMap.put("message", "Product type updated succesfully.");
				returnMap.put("errorCode", "");

			}
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getPagniatedProductsList(SearchProductPagniationOutputbean searchProductBean) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Page<Product> productPage = null;

		int pageNumber = Math.subtractExact(searchProductBean.getPageNumber(), 1);
		Pageable pagable = PageRequest.of(pageNumber, searchProductBean.getPageSize());

		productPage = productRepo.findAll(new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate p = cb.conjunction();

				if (searchProductBean.getProductTypeId() > 0) {
					p = cb.and(p, cb.equal(root.join("productType").get("productTypeId"),
							searchProductBean.getProductTypeId()));
				}

				if (searchProductBean.getBrandId() > 0) {
					p = cb.and(p, cb.equal(root.join("brand").get("brandId"), searchProductBean.getBrandId()));
				}
				return p;
			}
		}, pagable);

		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("ProductList", productPage.getContent());
		responseMap.put("CurrentPage", productPage.getNumber() + 1);
		responseMap.put("TotalItems", productPage.getTotalElements());
		responseMap.put("TotalPages", productPage.getTotalPages());

		return responseMap;
	}

	@Override
	public Map<String, Object> getActiveProductTypes() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		responseMap.put("ProductTypeList", null);
		List<ProductType> productTypesList = null;

		productTypesList = productTypeRepo.findByStatus();
		responseMap.put("ProductTypeList", productTypesList);
		return responseMap;
	}

	@Override
	public Map<String, Object> getProductByProductId(Long productId) {
		Product product = null;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		responseMap.put("Product", null);
		if (productId > 0) {
			product = productRepo.findById(productId).orElse(null);
			if (product.getProductId() > 0) {
				responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				responseMap.put("Product", product);
			}
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> addProduct(ProductBean productBean) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ProductType productType = null;
		Brand brand = null;
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		productType = productTypeRepo.findById(productBean.getProductTypeId()).orElse(null);
		brand = brandRepo.findById(productBean.getBrandId()).orElse(null);

		if (productType != null && brand != null && productType.getProductTypeId() > 0 && brand.getBrandId() > 0) {
			Product product = new Product();
			product.setProductType(productType);
			product.setBrand(brand);
			product.setTicketPrefix(productBean.getTicketPrefix());
			product.setProductName(productBean.getProductName());
			product.setWarrantyInYears(productBean.getWarrantyInYears());
			product.setStatus(false); // User should manually activate product using edit Product
			product = productRepo.save(product);
			if (product.getProductId() > 0) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				returnMap.put("message", "Product added successfully!");
			} else {
				returnMap.put("message", "Failed to add product. Try again!");
				returnMap.put("errorCode", "WSEM007");
			}
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> updateProduct(ProductBean productBean) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ProductType productType = null;
		Brand brand = null;
		Product product = null;
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

		if (productBean != null && productBean.getProductId() > 0) {
			product = productRepo.findById(productBean.getProductId()).orElse(null);
			if (product != null && product.getProductId() > 0) {
				productType = productTypeRepo.findById(productBean.getProductTypeId()).orElse(null);
				brand = brandRepo.findById(productBean.getBrandId()).orElse(null);

				if (productType != null && brand != null && productType.getProductTypeId() > 0
						&& brand.getBrandId() > 0) {
					product.setProductType(productType);
					product.setBrand(brand);
					product.setTicketPrefix(productBean.getTicketPrefix());
					product.setProductName(productBean.getProductName());
					product.setStatus(productBean.isStatus());
					product = productRepo.save(product);
					if (product.getProductId() > 0) {
						returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
						returnMap.put("message", "Product updated successfully!");
					} else {
						returnMap.put("message", "Failed to update product. Try again!");
						returnMap.put("errorCode", "WSEM009");
					}
				}
			}
		} else {
			returnMap.put("message", "Failed to edit product. Try again!");
			returnMap.put("errorCode", "WSEM008");
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> deleteProduct(Long productId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		if (productId > 0) {
			productRepo.deleteById(productId);
			returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
			returnMap.put("message", "Product deleted successfully.");
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getProductTypesByAssignedProductCatagory(Long userId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		returnMap.put("message", "Failed to load product List");

		User user = userRepo.findById(userId).orElse(null);
		if (user != null && user.getUserId() > 0) {
			List<ProductType> productTypeList = null;
			UserProductCatagoryLink upcl = null;

			if (user.getRole().getrolename().equals(ApplicationConstants.RoleConstants.ROLE_ADMIN)) {
				// return all product types
				productTypeList = productTypeRepo.findAll();
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				returnMap.put("message", "");
				returnMap.put("ProductTypeList", productTypeList);
			} else if (user.getRole().getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_ENGINEER) || user.getRole().getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_ENGINEER)) {
				User manager = user.getManager();
				if (manager.getUserId() > 0) {
					upcl = userProductCatagoryLinkRepo.findByUserId(manager.getUserId());
					if (upcl.getUserProductCatagoryLinkId() > 0) {
						productTypeList = productTypeRepo
								.findAllByProductCatagory(upcl.getProductCatagory().getProductCatagoryId());
						if (productTypeList != null && productTypeList.size() > 0) {
							returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
							returnMap.put("message", "");
							returnMap.put("ProductTypeList", productTypeList);
						}
					}
				}
			} else if (user.getRole().getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SALES_MANAGER) || user.getRole().getrolename().equals(ApplicationConstants.RoleConstants.ROLE_SERVICE_MANAGER)) {
				// if roles are Managers
				upcl = userProductCatagoryLinkRepo.findByUserId(user.getUserId());
				if (upcl.getUserProductCatagoryLinkId() > 0) {
					productTypeList = productTypeRepo
							.findAllByProductCatagory(upcl.getProductCatagory().getProductCatagoryId());
					if (productTypeList != null && productTypeList.size() > 0) {
						returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
						returnMap.put("message", "");
						returnMap.put("ProductTypeList", productTypeList);
					}
				}
			} else {
				returnMap.put("message", "No Product Catagory is assined to you or your manager.");
				returnMap.put("errorCode", "WSEM010");
			}
		} else {
			returnMap.put("message", "Failed to load product types List");
			returnMap.put("errorCode", "WSEM009");
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> getProductByBrandIdAndProductTypeId(Long brandId, Long productTypeId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
		returnMap.put("message", "Failed to load products List");
		List<Product> productList = null;
		
		if(brandId > 0 && productTypeId > 0) {
			productList = productRepo.findAll(new Specification<Product>() {
				@Override
				public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
					Predicate p = cb.conjunction();

					if (brandId > 0) {
						p = cb.and(p, cb.equal(root.join("brand").get("brandId"),	brandId));
					}

					if (productTypeId > 0) {
						p = cb.and(p, cb.equal(root.join("productType").get("productTypeId"),	productTypeId));
					}
					return p;
				}
			});
			
			if(productList != null && productList.size() > 0) {
				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
				returnMap.put("message", "");
				returnMap.put("ProductsList", productList);
			}
		}
		return returnMap;
	}

}
