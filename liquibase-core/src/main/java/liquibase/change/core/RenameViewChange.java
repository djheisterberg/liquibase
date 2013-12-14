package liquibase.change.core;

import liquibase.change.*;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RenameViewStatement;

/**
 * Renames an existing view.
 */
@DatabaseChange(name="renameView", description = "Renames an existing view", priority = ChangeMetaData.PRIORITY_DEFAULT, appliesTo = "view")
public class RenameViewChange extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String oldViewName;
    private String newViewName;

    @DatabaseChangeProperty(mustEqualExisting ="view.catalog", since = "3.0")
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting ="view.schema")
    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting = "view", description = "Name of the view to rename")
    public String getOldViewName() {
        return oldViewName;
    }

    public void setOldViewName(String oldViewName) {
        this.oldViewName = oldViewName;
    }

    @DatabaseChangeProperty(description = "Name to rename the view to")
    public String getNewViewName() {
        return newViewName;
    }

    public void setNewViewName(String newViewName) {
        this.newViewName = newViewName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new RenameViewStatement(getCatalogName(), getSchemaName(), getOldViewName(), getNewViewName())};
    }

    @Override
    protected Change[] createInverses() {
        RenameViewChange inverse = new RenameViewChange();
        inverse.setOldViewName(getNewViewName());
        inverse.setNewViewName(getOldViewName());

        return new Change[]{
                inverse
        };
    }

    @Override
    public String getConfirmationMessage() {
        return "View " + oldViewName + " renamed to " + newViewName;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return STANDARD_CHANGELOG_NAMESPACE;
    }
}
