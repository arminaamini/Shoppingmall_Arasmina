package controller;

import javax.swing.JFrame;

import commons.OperationResult;
import service.ProductService;
import view.ProductFormDialog;
import view.ProductManagementPanel;

import java.math.BigDecimal;
import java.util.List;
import model.Product;

public class AdminProductController {
    
    private ProductManagementPanel view;
    private ProductService service;
    private JFrame frame;

    public AdminProductController(JFrame frame,ProductManagementPanel view,
        ProductService service){

            this.frame = frame;
            this.view = view;
            this.service = service;

            addListeners();
            refresh();
        }
        private void addListeners(){
            view.onRefresh(() -> refresh());

            view.onSortA(() -> {
                OperationResult<List<Product>> r = service.sortByPrice(true);
                if(r.getSuccess()) view.setProducts(r.getData());
                else view.showError(r.getMessage());
            });

            view.onAdd(() -> addProduct());
            view.onEdite(() -> editProduct());
            view.onDelete(() -> deleteProduct());
        }
        private void refresh(){
            OperationResult<List<Product>> r = service.listProducts();
            if(r.getSuccess()) view.setProducts(r.getData());
            else view.showError(r.getMessage());

        }
        private void addProduct(){
            ProductFormDialog dlg = new ProductFormDialog(frame, "Add Product");
            dlg.setVisible(true);

            if(!dlg.isConfirmed()) return;

            try{
                Product p = new Product();
                p.setName(dlg.getNameInput());
                p.setCategory(dlg.getCategoryInput());
                p.setPrice((new BigDecimal(dlg.getPriceInput())));
                p.setStockQuantity(Integer.parseInt(dlg.getStockInput()));
                p.setDescription(dlg.getDescriptionInput());

                OperationResult<Void> r = service.add(p);
                if(r.getSuccess()){
                    view.showInfo("added");
                    refresh();
                }
                else{
                    view.showError(r.getMessage());
                }
            }catch(Exception e){
                view.showError("invalid input");
            }
        }
        private void editProduct(){
            String id = view.getSelectedProdeuct();
            if(id == null){
                view.showError("select a product ");
                return;
            }
            ProductFormDialog dlg = new ProductFormDialog(frame, "Edit Product");
            dlg.setVisible(true);

            if(!dlg.isConfirmed()) return;

            try{
                Product p = new Product();
                p.setProductId(id);
                p.setName(dlg.getNameInput());
                p.setCategory(dlg.getCategoryInput());
                p.setPrice((new BigDecimal(dlg.getPriceInput())));
                p.setStockQuantity(Integer.parseInt(dlg.getStockInput()));
                p.setDescription(dlg.getDescriptionInput());

                OperationResult<Void> r = service.update(p);
                if(r.getSuccess()){
                    view.showInfo("updated");
                    refresh();
                }
                else{
                    view.showError(r.getMessage());
                }
            }catch(Exception e){
                view.showError("invalid input");
            }

        }
        
        private void deleteProduct(){
            
            String id = view.getSelectedProdeuct();
            if(id == null){
                view.showError("select a product ");
                return;
            }
        OperationResult<Void> result = service.delete(id);

        if(!result.getSuccess()){
            view.showError(result.getMessage());
            return;
        }
        view.showInfo("deleted");
        refresh();

        }
    
}
