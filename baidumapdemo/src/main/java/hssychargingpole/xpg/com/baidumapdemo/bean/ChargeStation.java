package hssychargingpole.xpg.com.baidumapdemo.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/13.
 */

public class ChargeStation extends DataSupport implements Serializable{

    private static final long serialVersionUID = 613994713938447594L;
    private String name;
//    private LatLng latLng;
    private Double latitude;//维度
    private Double longitude;//经度
    private String equipmentType;
    private  int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }



    @Override
    public String toString() {
        return "ChargeStation{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", equipmentType='" + equipmentType + '\'' +
                ", id=" + id +
                '}';
    }
}
