/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.poc.shoppingpos.db;

import android.util.Log;

import com.poc.shoppingpos.db.entity.ProductEntity;
import com.poc.shoppingpos.utils.CommonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final String[] FIRST = new String[]{
            "Special edition", "New", "Cheap", "Quality", "Used"};
    private static final String[] SECOND = new String[]{
            "Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle"};
    private static final String[] DESCRIPTION = new String[]{
            "is finally here", "is recommended by Stan S. Stanman",
            "is the best sold product on Mêlée Island", "is \uD83D\uDCAF", "is ❤️", "is fine"};
    private static final String[] COMMENTS = new String[]{
            "Comment 1", "Comment 2", "Comment 3", "Comment 4", "Comment 5", "Comment 6"};

    public static List<ProductEntity> generateProducts() {
        List<ProductEntity> products = new ArrayList<>(FIRST.length * SECOND.length);
        Random rnd = new Random();
        for (int i = 0; i < FIRST.length; i++) {
            for (int j = 0; j < SECOND.length; j++) {
                ProductEntity product = new ProductEntity();
                int id = FIRST.length * i + j + 1;
                Log.d("Barcode","Product ID : " + id);
                product.setProductID(id);
                product.setProductName(FIRST[i] + " " + SECOND[j]);
                product.setProductDescription(product.getProductName() + " " + DESCRIPTION[j]);
                product.setMrp(CommonUtils.roundTwoDecimals(rnd.nextDouble() * 100));
                product.setSellingPrice(product.getMrp() > 2 ? (product.getMrp() - 1) :product.getMrp());
                String barcode = randomAlphaNumeric(8);
                Log.d("Barcode",FIRST[i] + " " + SECOND[j] + " For Barcode : " + barcode);
                product.setBarcode(barcode);
                product.setAvlQuantity(240);
                product.setImageURL("");
                products.add(product);
            }
        }
        return products;
    }

//    public static List<CommentEntity> generateCommentsForProducts(
//            final List<ProductEntity> products) {
//        List<CommentEntity> comments = new ArrayList<>();
//        Random rnd = new Random();
//
//        for (Product product : products) {
//            int commentsNumber = rnd.nextInt(5) + 1;
//            for (int i = 0; i < commentsNumber; i++) {
//                CommentEntity comment = new CommentEntity();
//                comment.setProductId(product.getId());
//                comment.setText(COMMENTS[i] + " for " + product.getName());
//                comment.setPostedAt(new Date(System.currentTimeMillis()
//                        - TimeUnit.DAYS.toMillis(commentsNumber - i) + TimeUnit.HOURS.toMillis(i)));
//                comments.add(comment);
//            }
//        }
//
//        return comments;
//    }

    private static String randomAlphaNumeric(int length) {
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
