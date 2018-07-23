package debug;

import com.android.common.base.BaseApplication;
import com.mob.MobSDK;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/2/15 20:09
 * @version V1.2.0
 * @name GirlsApplication
 */
public class RxyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
    }


}
