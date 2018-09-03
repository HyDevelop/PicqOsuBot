package cc.moecraft.icq.plugins.osubot.database.service;

import cc.moecraft.icq.plugins.osubot.database.model.HoUserData;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

public interface HoUserDataService
{
    public HoUserData findByOsu(long osuId);

    /**
     * find model by primary key
     *
     * @param id
     * @return
     */
    public HoUserData findById(Object id);


    /**
     * find all model
     *
     * @return all <HoUserData
     */
    public List<HoUserData> findAll();


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
    public boolean delete(HoUserData model);


    /**
     * save model to database
     *
     * @param model
     * @return
     */
    public boolean save(HoUserData model);


    /**
     * save or update model
     *
     * @param model
     * @return if save or update success
     */
    public boolean saveOrUpdate(HoUserData model);


    /**
     * update data model
     *
     * @param model
     * @return
     */
    public boolean update(HoUserData model);


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