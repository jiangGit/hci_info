/**
 * 
 */
package org.scauhci.official;

import java.util.Timer;
import java.util.TimerTask;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.mongo.MongoDao;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.scauhci.official.service.ApiService;
import org.scauhci.official.service.CommonMongoService;

/**
 * @author clarenceau
 *
 */
public class OfficialSetup implements Setup {

    /* (non-Javadoc)
     * @see org.nutz.mvc.Setup#destroy(org.nutz.mvc.NutConfig)
     */
    @Override
    public void destroy(NutConfig nc) {
        // TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see org.nutz.mvc.Setup#init(org.nutz.mvc.NutConfig)
     */
    @Override
    public void init(final NutConfig nutConfig) {
        
        Ioc ioc = new NutIoc(new JsonLoader("ioc/mysql.js"));
        Dao dao = ioc.get(NutDao.class, "dao");
        final ApiService apiService = new ApiService(dao);
        
        Timer timer = new Timer();       
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                nutConfig.getServletContext().setAttribute("API_KEYS", apiService.keys());               
            }
        }, 20*1000, 12L*60*60*60*1000);//12小时调度一下
    }
}
