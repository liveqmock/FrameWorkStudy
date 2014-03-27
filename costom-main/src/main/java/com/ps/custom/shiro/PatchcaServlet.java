package com.ps.custom.shiro;

import com.ps.utils.Exceptions;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Package com.ps.custom.shiro
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public class PatchcaServlet extends HttpServlet {

    private static final long serialVersionUID = -656795564528376490L;

    private static final Logger logger = LoggerFactory.getLogger(PatchcaServlet.class);

    private static int WIDTH = 110;
    private static int HEIGHT = 50;
    private static int MAX_LENGTH = 4;
    private static int MIN_LENGTH = 4;

    private static String CACHE_NAME = "_pcatcha_";
    private static String CACHE_FILE = "ehcache/ehcache-pcaptcha.xml";

    private static Cache cache;
    private static ConfigurableCaptchaService cs;

    @Override
    public void init() {
        cs = new ConfigurableCaptchaService();
        int width = NumberUtils.toInt(this.getInitParameter("width"), WIDTH);
        int height = NumberUtils.toInt(this.getInitParameter("height"), HEIGHT);
        cs.setWidth(width);
        cs.setHeight(height);

        int maxLength = NumberUtils.toInt(this.getInitParameter("maxLength"), MAX_LENGTH);
        int minLength = NumberUtils.toInt(this.getInitParameter("minLength"), MIN_LENGTH);
        RandomWordFactory randomWordFactory = new RandomWordFactory();
        randomWordFactory.setMaxLength(maxLength);
        randomWordFactory.setMinLength(minLength);
        cs.setWordFactory(randomWordFactory);
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource resource = resourcePatternResolver.getResource("classpath:" + CACHE_FILE);
        try {
            CacheManager cacheManager = CacheManager.create(resource.getInputStream());
            cache = cacheManager.getCache(CACHE_NAME);
            if (cache == null) {
                Configuration configuration = ConfigurationFactory.parseConfiguration(resource.getInputStream());
                cache = new Cache(configuration.getCacheConfigurations().get(CACHE_NAME));
                if (cache == null) {
                    cache = cacheManager.getCache(Cache.DEFAULT_CACHE_NAME);
                } else {
                    cacheManager.addCache(cache);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("缓存配置文件[" + CACHE_FILE + "]读取出错:" + Exceptions.getStackTraceAsString(e));
        } catch (CacheException e) {
            logger.error("创建缓存[" + CACHE_NAME + "]出错，请检查配置参数:" + Exceptions.getStackTraceAsString(e));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 清除缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);

        // 显示类型
        response.setContentType("image/png");

        OutputStream os = response.getOutputStream();
        String patchca = EncoderHelper.getChallangeAndWriteImage(cs, "png", os);

        // 放入缓存
        Element element = new Element(request.getSession().getId(), patchca);
        cache.put(element);

        os.flush();
        os.close();
    }

    /**
     * 检查验证码是否正确
     *
     * @param sessionId
     * @param code
     * @return
     */
    public static boolean validateCode(String sessionId, String code) {
        Element element = cache.get(sessionId);
        if (element != null) {
            String sourceCode = element.getObjectValue().toString();
            cache.remove(sessionId);
            return StringUtils.equalsIgnoreCase(code, sourceCode);
        }
        return false;
    }

    @Override
    public void destroy() {
        cs = null;
        CacheManager.getInstance().removeCache(CACHE_NAME);
    }
}