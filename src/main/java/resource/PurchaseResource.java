package resource;

import dao.PurchaseDao;
import pojo.Purchase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @ClassName PurchaseResource
 * @Description TODO
 * @Author lxyqaq @Email A00279565@student.ait.ie
 * @Date 2021/4/16 18:11
 * @Version 1.0
 */
@Path("/purchases")
public class PurchaseResource {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Purchase> getPurchases() {
        return PurchaseDao.instance.getAllPurchase();
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addPurchase(Purchase purchase) {
        PurchaseDao.instance.addPurchase(purchase);
    }

}
