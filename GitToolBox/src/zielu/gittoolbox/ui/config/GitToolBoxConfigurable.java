package zielu.gittoolbox.ui.config;

import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.ConfigurationException;
import javax.swing.JComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import zielu.gittoolbox.GitToolBoxConfig;
import zielu.gittoolbox.ResBundle;

public class GitToolBoxConfigurable extends BaseConfigurable {
    private volatile GitToolBoxForm form;

    @Nls
    @Override
    public String getDisplayName() {
        return ResBundle.getString("configurable.app.displayName");
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    private synchronized void initComponent() {
        if (form == null) {
            form = new GitToolBoxForm();
            form.init();
        }
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        initComponent();
        GitToolBoxConfig config = GitToolBoxConfig.getInstance();
        form.setPresenter(config.getPresenter());
        form.setShowGitStatus(config.showStatusWidget);
        form.setShowProjectViewStatus(config.showProjectViewStatus);
        return form.getContent();
    }

    @Override
    public boolean isModified() {
        GitToolBoxConfig config = GitToolBoxConfig.getInstance();
        boolean modified = config.isPresenterChanged(form.getPresenter());
        modified = modified || config.isShowStatusWidget(form.getShowGitStatus());
        modified = modified || config.isShowProjectViewStatus(form.getShowProjectViewStatus());
        setModified(modified);
        return super.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        initComponent();
        GitToolBoxConfig config = GitToolBoxConfig.getInstance();
        config.setPresenter(form.getPresenter());
        config.showStatusWidget = form.getShowGitStatus();
        config.showProjectViewStatus = form.getShowProjectViewStatus();
        config.fireChanged();
    }

    @Override
    public void reset() {
        initComponent();
        GitToolBoxConfig config = GitToolBoxConfig.getInstance();
        form.setPresenter(config.getPresenter());
    }

    @Override
    public synchronized void disposeUIResources() {
        form = null;
    }
}
