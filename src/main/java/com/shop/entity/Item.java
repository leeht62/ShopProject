package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item {
  @Id
  @Column(name="item_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable=false,length=50)
  private String itemNm;

  @Column(name="price",nullable=false)
  private int price;

  @Column(nullable=false)
  private int stockNumber;


  @CreatedBy
  @Column(updatable = false)
  private String createdBy;


  @Lob
  @Column(nullable=false)
  private String itemDetail;

  @Enumerated(EnumType.STRING)
  private ItemSellStatus itemSellStatus;
  private LocalDateTime regTime;
  private LocalDateTime updateTime;

  public void updateItem(ItemFormDto itemFormDto){
    this.itemNm = itemFormDto.getItemNm();
    this.price = itemFormDto.getPrice();
    this.stockNumber=itemFormDto.getStockNumber();
    this.itemDetail=itemFormDto.getItemDetail();
    this.itemSellStatus=itemFormDto.getItemSellStatus();
  }

  public void removeStock(int stockNumber){
    int restStock=this.stockNumber-stockNumber;
    if(restStock<0){
      throw new OutOfStockException("상품의 재고가 부족합니다.(현재 재고 수량"+this.stockNumber+")");
    }
    this.stockNumber=restStock;
  }

  public void addStock(int stockNumber){
    this.stockNumber+=stockNumber;
  }
}
