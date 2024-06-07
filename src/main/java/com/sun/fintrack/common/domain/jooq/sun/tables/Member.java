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
import sun.tables.records.MemberRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Member extends TableImpl<MemberRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>fintrack.MEMBER</code>
     */
    public static final Member MEMBER = new Member();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MemberRecord> getRecordType() {
        return MemberRecord.class;
    }

    /**
     * The column <code>fintrack.MEMBER.MB_SEQ</code>. 회원 일련번호
     */
    public final TableField<MemberRecord, Long> MB_SEQ = createField(DSL.name("MB_SEQ"), SQLDataType.BIGINT.nullable(false).identity(true), this, "회원 일련번호");

    /**
     * The column <code>fintrack.MEMBER.MB_EMAIL</code>. 이메일
     */
    public final TableField<MemberRecord, String> MB_EMAIL = createField(DSL.name("MB_EMAIL"), SQLDataType.VARCHAR(255).nullable(false), this, "이메일");

    /**
     * The column <code>fintrack.MEMBER.MB_NM</code>. 닉네임
     */
    public final TableField<MemberRecord, String> MB_NM = createField(DSL.name("MB_NM"), SQLDataType.VARCHAR(50).nullable(false), this, "닉네임");

    /**
     * The column <code>fintrack.MEMBER.MB_ROLE</code>. 권한
     */
    public final TableField<MemberRecord, String> MB_ROLE = createField(DSL.name("MB_ROLE"), SQLDataType.VARCHAR(1).nullable(false), this, "권한");

    /**
     * The column <code>fintrack.MEMBER.MB_VALID</code>. 유효여부
     */
    public final TableField<MemberRecord, String> MB_VALID = createField(DSL.name("MB_VALID"), SQLDataType.VARCHAR(1).nullable(false), this, "유효여부");

    /**
     * The column <code>fintrack.MEMBER.CRE_DT</code>. 등록일시
     */
    public final TableField<MemberRecord, LocalDateTime> CRE_DT = createField(DSL.name("CRE_DT"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "등록일시");

    /**
     * The column <code>fintrack.MEMBER.UPD_DT</code>. 수정일시
     */
    public final TableField<MemberRecord, LocalDateTime> UPD_DT = createField(DSL.name("UPD_DT"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "수정일시");

    private Member(Name alias, Table<MemberRecord> aliased) {
        this(alias, aliased, null);
    }

    private Member(Name alias, Table<MemberRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>fintrack.MEMBER</code> table reference
     */
    public Member(String alias) {
        this(DSL.name(alias), MEMBER);
    }

    /**
     * Create an aliased <code>fintrack.MEMBER</code> table reference
     */
    public Member(Name alias) {
        this(alias, MEMBER);
    }

    /**
     * Create a <code>fintrack.MEMBER</code> table reference
     */
    public Member() {
        this(DSL.name("MEMBER"), null);
    }

    public <O extends Record> Member(Table<O> child, ForeignKey<O, MemberRecord> key) {
        super(child, key, MEMBER);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Fintrack.FINTRACK;
    }

    @Override
    public Identity<MemberRecord, Long> getIdentity() {
        return (Identity<MemberRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<MemberRecord> getPrimaryKey() {
        return Keys.KEY_MEMBER_PRIMARY;
    }

    @Override
    public Member as(String alias) {
        return new Member(DSL.name(alias), this);
    }

    @Override
    public Member as(Name alias) {
        return new Member(alias, this);
    }

    @Override
    public Member as(Table<?> alias) {
        return new Member(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Member rename(String name) {
        return new Member(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Member rename(Name name) {
        return new Member(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Member rename(Table<?> name) {
        return new Member(name.getQualifiedName(), null);
    }
}
