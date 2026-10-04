package reports;

public abstract class ReportTemplate {
    public final void generate() {
        loadData();
        formatData();
        export();
    }
    protected abstract void loadData();
    protected abstract void formatData();
    protected abstract void export();
}
