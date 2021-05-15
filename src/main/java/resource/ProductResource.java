package resource;

import dao.ProductDao;
import pojo.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * @ClassName ProductResource
 * @Description TODO
 * @Author lxyqaq @Email A00279565@student.ait.ie
 * @Date 2021/4/12 16:24
 * @Version 1.0
 */
@Path("/products")
public class ProductResource {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> getAllProducts() {
        return ProductDao.instance.findAllProduct();
    }

    @GET
    @Path("{categoryName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> getProductsByCatogery(@PathParam("categoryName") String name) {
        String first = name.substring(0, 1).toUpperCase();
        String after = name.substring(1);
        return ProductDao.instance.findProductByCategory(first + after);
    }

    @GET
    @Path("product/{productId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Product getProduct(@PathParam("productId") String id) {
        return ProductDao.instance.findProduct(Integer.parseInt(id));
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addProduct(Product product) {
        ProductDao.instance.saveProduct(product);
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateProduct(Product product) {
        ProductDao.instance.updateProduct(product);
    }

    @DELETE
    @Path("{productId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void deleteProduct(@PathParam("productId") String id) {
        ProductDao.instance.deleteProduct(Integer.parseInt(id));
    }

}
