/*
 * This file is generated by jOOQ.
 */
package sun.tables;


import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import sun.Fintrack;
import sun.Keys;
import sun.tables.records.IncomeRecord;


/**
 * 수입 테이블
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Income extends TableImpl<IncomeRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>fintrack.INCOME</code>
     */
    public static final Income INCOME = new Income();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IncomeRecord> getRecordType() {
        return IncomeRecord.class;
    }

    /**
     * The column <code>fintrack.INCOME.IC_SEQ</code>. 수입 일련번호
     */
    public final TableField<IncomeRecord, Long> IC_SEQ = createField(DSL.name("IC_SEQ"), SQLDataType.BIGINT.nullable(false).identity(true), this, "수입 일련번호");

    /**
     * The column <code>fintrack.INCOME.MB_SEQ</code>. 회원 일련번호
     */
    public final TableField<IncomeRecord, Long> MB_SEQ = createField(DSL.name("MB_SEQ"), SQLDataType.BIGINT.nullable(false), this, "회원 일련번호");

    /**
     * The column <code>fintrack.INCOME.IC_CTT</code>. 수입 내용
     */
    public final TableField<IncomeRecord, String> IC_CTT = createField(DSL.name("IC_CTT"), SQLDataType.VARCHAR(255).nullable(false), this, "수입 내용");

    /**
     * The column <code>fintrack.INCOME.IC_PRC</code>. 수입 금액
     */
    public final TableField<IncomeRecord, Long> IC_PRC = createField(DSL.name("IC_PRC"), SQLDataType.BIGINT.nullable(false), this, "수입 금액");

    /**
     * The column <code>fintrack.INCOME.IC_DT</code>. 수입일시
     */
    public final TableField<IncomeRecord, LocalDateTime> IC_DT = createField(DSL.name("IC_DT"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "수입일시");

    /**
     * The column <code>fintrack.INCOME.AS_SEQ</code>. 자산 일련번호
     */
    public final TableField<IncomeRecord, Long> AS_SEQ = createField(DSL.name("AS_SEQ"), SQLDataType.BIGINT.nullable(false), this, "자산 일련번호");

    /**
     * The column <code>fintrack.INCOME.CTG_ID</code>. 카테고리 아이디
     */
    public final TableField<IncomeRecord, Long> CTG_ID = createField(DSL.name("CTG_ID"), SQLDataType.BIGINT.nullable(false), this, "카테고리 아이디");

    /**
     * The column <code>fintrack.INCOME.CRE_DT</code>. 등록일시
     */
    public final TableField<IncomeRecord, LocalDateTime> CRE_DT = createField(DSL.name("CRE_DT"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "등록일시");

    /**
     * The column <code>fintrack.INCOME.UPD_DT</code>. 수정일시
     */
    public final TableField<IncomeRecord, LocalDateTime> UPD_DT = createField(DSL.name("UPD_DT"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "수정일시");

    private Income(Name alias, Table<IncomeRecord> aliased) {
        this(alias, aliased, null);
    }

    private Income(Name alias, Table<IncomeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("수입 테이블"), TableOptions.table());
    }

    /**
     * Create an aliased <code>fintrack.INCOME</code> table reference
     */
    public Income(String alias) {
        this(DSL.name(alias), INCOME);
    }

    /**
     * Create an aliased <code>fintrack.INCOME</code> table reference
     */
    public Income(Name alias) {
        this(alias, INCOME);
    }

    /**
     * Create a <code>fintrack.INCOME</code> table reference
     */
    public Income() {
        this(DSL.name("INCOME"), null);
    }

    public <O extends Record> Income(Table<O> child, ForeignKey<O, IncomeRecord> key) {
        super(child, key, INCOME);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Fintrack.FINTRACK;
    }

    @Override
    public Identity<IncomeRecord, Long> getIdentity() {
        return (Identity<IncomeRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<IncomeRecord> getPrimaryKey() {
        return Keys.KEY_INCOME_PRIMARY;
    }

    @Override
    public Income as(String alias) {
        return new Income(DSL.name(alias), this);
    }

    @Override
    public Income as(Name alias) {
        return new Income(alias, this);
    }

    @Override
    public Income as(Table<?> alias) {
        return new Income(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Income rename(String name) {
        return new Income(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Income rename(Name name) {
        return new Income(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Income rename(Table<?> name) {
        return new Income(name.getQualifiedName(), null);
    }
}
