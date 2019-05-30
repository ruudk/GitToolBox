package zielu.gittoolbox;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.diagnostic.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import zielu.gittoolbox.util.AppUtil;

public class GitToolBoxApp implements BaseComponent {
  private final Logger log = Logger.getInstance(getClass());
  private final ScheduledExecutorService autoFetchExecutor;
  private final ScheduledExecutorService tasksExecutor;

  GitToolBoxApp() {
    autoFetchExecutor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(),
        new ThreadFactoryBuilder().setDaemon(true).setNameFormat("GitToolBox-AutoFetch-%s").build()
    );
    log.debug("Created auto-fetch executor: ", autoFetchExecutor);
    tasksExecutor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(),
        new ThreadFactoryBuilder().setDaemon(true).setNameFormat("GitToolBox-Task-%s").build()
    );
    log.debug("Created tasks executor: ", tasksExecutor);
  }

  @Override
  public void disposeComponent() {
    autoFetchExecutor.shutdown();
    tasksExecutor.shutdown();
  }

  public static GitToolBoxApp getInstance() {
    return AppUtil.getComponent(GitToolBoxApp.class);
  }

  public ScheduledExecutorService autoFetchExecutor() {
    return autoFetchExecutor;
  }

  public ScheduledExecutorService tasksExecutor() {
    return tasksExecutor;
  }
}
