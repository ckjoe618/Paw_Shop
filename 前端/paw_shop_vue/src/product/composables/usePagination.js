import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

export function usePagination(allItems, options = {}) {
    const route = useRoute()
    const router = useRouter()

    const itemsPerPage = options.itemsPerPage || 9
    const currentPage = ref(Number(route.query.page) || 1)

    const paginatedItems = computed(() => {
        const start = (currentPage.value - 1) * itemsPerPage
        const end = start + itemsPerPage
        return allItems.value.slice(start, end)
    })

    const totalPages = computed(() => {
        return Math.ceil(allItems.value.length / itemsPerPage)
    })

    function updateQuery(pageOverride) {
        router.replace({
            path: route.path,
            query: {
                ...route.query,
                page: pageOverride || currentPage.value
            }
        })
    }

    watch(() => route.query.page, (page) => {
        currentPage.value = Number(page) || 1
    })

    return {
        currentPage,
        paginatedItems,
        totalPages,
        updateQuery
    }
}