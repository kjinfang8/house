package com.yu.house.common.model;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class House {

	private Long id;
	private Integer type;
	private Integer price;
	private String name;
	private String images;
	private Integer area;
	private Integer beds;
	private Integer baths;
	private Double rating;

	private Integer roundRating = 0;
	private String remarks;
	private String properties;
	private String floorPlan;
	private String tags;
	private Date createTime;
	private Integer cityId;
	private Integer communityId;
	private String address;
	private String firstImg;
	private String priceStr;
	private String typeStr;
	private Long userId;
	private boolean bookmarked;
	private Integer state;
	private List<Long> ids;
	private String sort = "time_desc";// price_desc,price_asc,time_desc

	private List<String> imageList = Lists.newArrayList();
	private List<String> floorPlanList = Lists.newArrayList();
	private List<String> featureList = Lists.newArrayList();
	private List<MultipartFile> houseFiles;
	private List<MultipartFile> floorPlanFiles;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
		if (Objects.equal(type, 1) && Objects.equal(type, 0)) {
			this.typeStr = "For Sale";
		} else {
			this.typeStr = "For Rent";
		}
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
		this.priceStr = this.price + "万";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
		if (!Strings.isNullOrEmpty(images)) {
			List<String> list = Splitter.on(",").splitToList(images);
			if (list.size() > 0) {
				this.firstImg = list.get(0);
				this.imageList = list;
			}
		}
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Integer getBeds() {
		return beds;
	}

	public void setBeds(Integer beds) {
		this.beds = beds;
	}

	public Integer getBaths() {
		return baths;
	}

	public void setBaths(Integer baths) {
		this.baths = baths;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
		this.roundRating = (int) Math.round(rating);
	}

	public Integer getRoundRating() {
		return roundRating;
	}

	public void setRoundRating(Integer roundRating) {
		this.roundRating = roundRating;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
		this.featureList = Splitter.on(",").splitToList(properties);
	}

	public String getFloorPlan() {
		return floorPlan;
	}

	public void setFloorPlan(String floorPlan) {
		this.floorPlan = floorPlan;
		if (!Strings.isNullOrEmpty(floorPlan)) {
			this.floorPlanList = Splitter.on(",").splitToList(floorPlan);
		}
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstImg() {
		return firstImg;
	}

	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public List<String> getFloorPlanList() {
		return floorPlanList;
	}

	public void setFloorPlanList(List<String> floorPlanList) {
		this.floorPlanList = floorPlanList;
	}

	public List<String> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(List<String> featureList) {
		this.featureList = featureList;
		this.properties = Joiner.on(",").join(featureList);
	}

	public List<MultipartFile> getHouseFiles() {
		return houseFiles;
	}

	public void setHouseFiles(List<MultipartFile> houseFiles) {
		this.houseFiles = houseFiles;
	}

	public List<MultipartFile> getFloorPlanFiles() {
		return floorPlanFiles;
	}

	public void setFloorPlanFiles(List<MultipartFile> floorPlanFiles) {
		this.floorPlanFiles = floorPlanFiles;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isBookmarked() {
		return bookmarked;
	}

	public void setBookmarked(boolean bookmarked) {
		this.bookmarked = bookmarked;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "House [id=" + id + ", type=" + type + ", price=" + price + ", name=" + name + ", images=" + images
				+ ", area=" + area + ", beds=" + beds + ", baths=" + baths + ", rating=" + rating + ", roundRating="
				+ roundRating + ", remarks=" + remarks + ", properties=" + properties + ", floorPlan=" + floorPlan
				+ ", tags=" + tags + ", createTime=" + createTime + ", cityId=" + cityId + ", communityId="
				+ communityId + ", address=" + address + ", firstImg=" + firstImg + ", imageList=" + imageList
				+ ", floorPlanList=" + floorPlanList + ", featureList=" + featureList + ", houseFiles=" + houseFiles
				+ ", floorPlanFiles=" + floorPlanFiles + ", priceStr=" + priceStr + ", typeStr=" + typeStr + ", userId="
				+ userId + ", bookmarked=" + bookmarked + ", state=" + state + ", ids=" + ids + ", sort=" + sort + "]";
	}
}
