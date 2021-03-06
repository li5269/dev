package jsf;

import entity.Post;
import entity.Reply;
import java.io.Serializable;
import java.util.Collection;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;


import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;


@Named("postController")
@SessionScoped
public class PostController implements Serializable {


    private Post current;
    private DataModel items = null;
    @EJB private jsf.PostFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PostController() {
    }

    public Post getSelected() {
        recreateModel();
        if (current == null) {
            current = new Post();
            current.setPostPK(new entity.PostPK());
            selectedItemIndex = -1;
        }
        return current;
    }
    
    
    private PostFacade getFacade() {
        return ejbFacade;
    }
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(30) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Post)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    public String MyprepareView() {
        current = (Post)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ThePost";
    }
    
    
    
    

    public String prepareCreate() {
        current = new Post();
        current.setPostPK(new entity.PostPK());
        selectedItemIndex = -1;
        return "Create";
    }
     public String MyprepareCreate() {
        current = new Post();
        current.setPostPK(new entity.PostPK());
        selectedItemIndex = -1;
        return "Post";
    }
    
    
    

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    
      public String Mycreate() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
            return MyprepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    
    public String prepareEdit() {
        current = (Post)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Post)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count-1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }
     public String Mynext() {
        getPagination().nextPage();
        recreateModel();
        return "Posts";
    }
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }
    public String Myprevious() {
        getPagination().previousPage();
        recreateModel();
        return "Posts";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    public SelectItem[] MygetItemsAvailableSelectOne(entity.PostPK id) {
        return JsfUtil.MygetSelectItems(ejbFacade.find(id), true);
    }
    
    public Reply[] MygetReplyItems(Collection<Reply> replyCollection) {
        
        int size = replyCollection.size();
       
        Reply[] replies = replyCollection.toArray(new Reply[size]);
       
        return replies;
    
    }
    
    
    public Post getPost(entity.PostPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass=Post.class)
    public static class PostControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PostController controller = (PostController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "postController");
            return controller.getPost(getKey(value));
        }

        entity.PostPK getKey(String value) {
            entity.PostPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entity.PostPK();
            key.setPostId(Integer.parseInt(values[0]));
            key.setPostName(values[1]);
            return key;
        }

        String getStringKey(entity.PostPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPostId());
            sb.append(SEPARATOR);
            sb.append(value.getPostName());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Post) {
                Post o = (Post) object;
                return getStringKey(o.getPostPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+Post.class.getName());
            }
        }

    }

}
