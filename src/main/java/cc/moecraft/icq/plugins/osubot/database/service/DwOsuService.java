package cc.moecraft.icq.plugins.osubot.database.service;

import cc.moecraft.icq.plugins.osubot.database.model.DwOsu;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

public interface DwOsuService  {

    /**
     * find model by primary key
     *
     * @param id
     * @return
     */
    public DwOsu findById(Object id);


    /**
     * find all model
     *
     * @return all <DwOsu
     */
    public List<DwOsu> findAll();


    /**
     * delete model by primary key
     *
     * @param id
     * @return success
     */
    public boolean deleteById(Object id);


    /**
     * delete model
     *
     * @param model
     * @return
     */
    public boolean delete(DwOsu model);


    /**
     * save model to database
     *
     * @param model
     * @return
     */
    public boolean save(DwOsu model);


    /**
     * save or update model
     *
     * @param model
     * @return if save or update success
     */
    public boolean saveOrUpdate(DwOsu model);


    /**
     * update data model
     *
     * @param model
     * @return
     */
    public boolean update(DwOsu model);


    public void join(Page<? extends Model> page, String joinOnField);
    public void join(Page<? extends Model> page, String joinOnField, String[] attrs);
    public void join(Page<? extends Model> page, String joinOnField, String joinName);
    public void join(Page<? extends Model> page, String joinOnField, String joinName, String[] attrs);
    public void join(List<? extends Model> models, String joinOnField);
    public void join(List<? extends Model> models, String joinOnField, String[] attrs);
    public void join(List<? extends Model> models, String joinOnField, String joinName);
    public void join(List<? extends Model> models, String joinOnField, String joinName, String[] attrs);
    public void join(Model model, String joinOnField);
    public void join(Model model, String joinOnField, String[] attrs);
    public void join(Model model, String joinOnField, String joinName);
    public void join(Model model, String joinOnField, String joinName, String[] attrs);

    public void keep(Model model, String... attrs);
    public void keep(List<? extends Model> models, String... attrs);
}