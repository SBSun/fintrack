/*
 * This file is generated by jOOQ.
 */
package sun.tables.records;


import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

import sun.tables.Category;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class CategoryRecord extends UpdatableRecordImpl<CategoryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>fintrack.CATEGORY.CTG_ID</code>. 카테고리 아이디
     */
    public CategoryRecord setCtgId(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>fintrack.CATEGORY.CTG_ID</code>. 카테고리 아이디
     */
    public Long getCtgId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>fintrack.CATEGORY.CTG_NM</code>. 카테고리명
     */
    public CategoryRecord setCtgNm(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>fintrack.CATEGORY.CTG_NM</code>. 카테고리명
     */
    public String getCtgNm() {
        return (String) get(1);
    }

    /**
     * Setter for <code>fintrack.CATEGORY.CTG_ORD</code>. 카테고리 순서
     */
    public CategoryRecord setCtgOrd(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>fintrack.CATEGORY.CTG_ORD</code>. 카테고리 순서
     */
    public Integer getCtgOrd() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>fintrack.CATEGORY.CTG_TYP</code>. 카테고리 타입
     */
    public CategoryRecord setCtgTyp(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>fintrack.CATEGORY.CTG_TYP</code>. 카테고리 타입
     */
    public String getCtgTyp() {
        return (String) get(3);
    }

    /**
     * Setter for <code>fintrack.CATEGORY.MB_SEQ</code>. 회원 일련번호
     */
    public CategoryRecord setMbSeq(Long value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>fintrack.CATEGORY.MB_SEQ</code>. 회원 일련번호
     */
    public Long getMbSeq() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>fintrack.CATEGORY.CRE_DT</code>. 등록일시
     */
    public CategoryRecord setCreDt(LocalDateTime value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>fintrack.CATEGORY.CRE_DT</code>. 등록일시
     */
    public LocalDateTime getCreDt() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>fintrack.CATEGORY.UPD_DT</code>. 수정일시
     */
    public CategoryRecord setUpdDt(LocalDateTime value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>fintrack.CATEGORY.UPD_DT</code>. 수정일시
     */
    public LocalDateTime getUpdDt() {
        return (LocalDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CategoryRecord
     */
    public CategoryRecord() {
        super(Category.CATEGORY);
    }

    /**
     * Create a detached, initialised CategoryRecord
     */
    public CategoryRecord(Long ctgId, String ctgNm, Integer ctgOrd, String ctgTyp, Long mbSeq, LocalDateTime creDt, LocalDateTime updDt) {
        super(Category.CATEGORY);

        setCtgId(ctgId);
        setCtgNm(ctgNm);
        setCtgOrd(ctgOrd);
        setCtgTyp(ctgTyp);
        setMbSeq(mbSeq);
        setCreDt(creDt);
        setUpdDt(updDt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised CategoryRecord
     */
    public CategoryRecord(sun.tables.pojos.Category value) {
        super(Category.CATEGORY);

        if (value != null) {
            setCtgId(value.getCtgId());
            setCtgNm(value.getCtgNm());
            setCtgOrd(value.getCtgOrd());
            setCtgTyp(value.getCtgTyp());
            setMbSeq(value.getMbSeq());
            setCreDt(value.getCreDt());
            setUpdDt(value.getUpdDt());
            resetChangedOnNotNull();
        }
    }
}
